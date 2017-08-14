package com.knowbag.azure

import java.io.{PrintWriter, StringWriter}
import java.net.URI
import java.time.{LocalDateTime, ZoneId}
import java.util
import java.util.Date
import java.util.concurrent.Executors

import com.microsoft.azure.storage.CloudStorageAccount
import com.microsoft.azure.storage.blob.SharedAccessBlobPermissions._
import com.microsoft.azure.storage.blob._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future, duration}
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by williame on 7/5/17.
  */
object BlobSASPerformance extends App {

  private val connection = "DefaultEndpointsProtocol=https;AccountName=felipesa;AccountKey=3QejsikK1NUjGSH18gvhPqL7j7WUpHhQjnoWK990wtH1w2FGZAmd2/nfVX27p9ByuwKX7E+he6GXt+v3EOudmA==;EndpointSuffix=core.windows.net"

  val blob: CloudBlockBlob = getblobClient(connection)
    .flatMap(getContainer(_, "test"))
    .flatMap(getBlob(_, "test.txt"))
    .get

  val executor = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))

  val values = (1 to 10) map { i =>
    Future {
      getSASToken(util.EnumSet.of(READ))(connection)(getDatePlusOneHour)(blob)
        .map { s =>
          println(i, s)
          val blob = new CloudBlockBlob(new URI(s))
          blob.downloadText()
        }
        .recover {
          case e: Exception => {
            val s = new StringWriter()
            e.printStackTrace(new PrintWriter(s))
            s.toString
          }
        }
        .map(t => t.trim == "this is a test")
        .getOrElse(false)
    }(executor)
  }

  val seq = Future.sequence(values).map(v => v.foldLeft(true)(_ && _))
  private val result = Await.result(seq, Duration.Inf)
  println(result)


  private def getSASToken(permissions: util.EnumSet[SharedAccessBlobPermissions])(connection: String)(date: () => Date)(blob: CloudBlockBlob) = Try {
    val sas = new SharedAccessBlobPolicy
    sas.setPermissions(permissions)
    sas.setSharedAccessExpiryTime(date())
    blob.getUri.toString + "?" + blob.generateSharedAccessSignature(sas, "")
  }

  private def getContainer(blobClient: CloudBlobClient, container: String) = Try {
    blobClient.getContainerReference(container)
  }

  private def getblobClient(connection: String) = Try {
    val storageAccount = CloudStorageAccount.parse(connection)
    storageAccount.createCloudBlobClient
  }

  private def getBlob(container: CloudBlobContainer, blob: String): Try[CloudBlockBlob] = Try {
    container.getBlockBlobReference(blob)
  }

  private def getDatePlusOneHour(): Date = {
    val time = LocalDateTime.now().plusHours(1)
    Date.from(time.atZone(ZoneId.systemDefault()).toInstant)
  }
}
