package com.knowbag.azure

import java.io.ByteArrayInputStream
import java.time.{LocalDateTime, ZoneId}
import java.util
import java.util.{Date, UUID}

import com.microsoft.azure.storage.SharedAccessAccountPermissions._
import com.microsoft.azure.storage.SharedAccessAccountResourceType.{CONTAINER, OBJECT}
import com.microsoft.azure.storage.SharedAccessAccountService.{BLOB, TABLE}
import com.microsoft.azure.storage.SharedAccessProtocols.HTTPS_HTTP
import com.microsoft.azure.storage._
import com.microsoft.azure.storage.table.{TableOperation, TableResult, TableServiceEntity}

import scala.util.Try

/**
  * Created by dev-williame on 4/14/17.
  */
object BlobApp extends App {

  val message = "this is a test".getBytes()

  private val connection = "DefaultEndpointsProtocol=https;AccountName=felipesa;AccountKey=3QejsikK1NUjGSH18gvhPqL7j7WUpHhQjnoWK990wtH1w2FGZAmd2/nfVX27p9ByuwKX7E+he6GXt+v3EOudmA==;EndpointSuffix=core.windows.net"
  getBlobContainerFromSAS(connection = connection, storageAccount = "felipesa", container = "scaleconf")(getDatePlusOneHour)
    .map(c => c.getBlockBlobReference("scaleconf.txt"))
    .map { b =>
      b.upload(new ByteArrayInputStream(message), message.length)
      b
    }
    .map(_.downloadText())
    .recover(printErrorAndReturn(""))
    .foreach(println)

  getTableFromSAS(connection, storageAccount = "felipesa", table = "test")(getDatePlusOneHour)
    .map { t =>
      val op = TableOperation.insert(new TableServiceEntity("felipe", UUID.randomUUID().toString))
      t.execute(op)
    }
    .recover(printErrorAndReturn(new TableResult(500)))
    .foreach(t => println(t.getResult.asInstanceOf[TableServiceEntity].getTimestamp))

  private def printErrorAndReturn[T](default: T): PartialFunction[Throwable, T] = {
    case e: Exception =>
      e.printStackTrace()
      default
  }

  private def getTableFromSAS(connection: String, storageAccount: String, table: String)(d: () => Date) =
    getSASToken(util.EnumSet.of(READ, ADD))(connection)(d)
      .flatMap(createSASAccount(storageAccount))
      .flatMap(tableSAS(table))

  private def getBlobContainerFromSAS(connection: String, storageAccount: String, container: String)(date: () => Date) =
    getSASToken(util.EnumSet.of(READ, WRITE, CREATE, UPDATE, ADD))(connection)(date)
      .flatMap(createSASAccount(storageAccount))
      .flatMap(blobContainerSas(container))

  private def blobContainerSas(container: String)(account: CloudStorageAccount) = Try {
    val bloblClient = account.createCloudBlobClient()
    bloblClient.getContainerReference(container)
  }

  private def tableSAS(table: String)(account: CloudStorageAccount) = Try {
    val tClient = account.createCloudTableClient()
    tClient.getTableReference(table)
  }

  private def createSASAccount(account: String)(sasToken: String) = Try {
    val accountSAS = new StorageCredentialsSharedAccessSignature(sasToken)
    new CloudStorageAccount(accountSAS, false, null, account)
  }

  private def getSASToken(permissions: util.EnumSet[SharedAccessAccountPermissions])(connection: String)(date: () => Date) = Try {
    val sas = new SharedAccessAccountPolicy()
    sas.setPermissions(permissions)
    sas.setProtocols(HTTPS_HTTP)
    sas.setResourceTypes(util.EnumSet.of(CONTAINER, OBJECT))
    sas.setServices(util.EnumSet.of(BLOB, TABLE))
    sas.setSharedAccessExpiryTime(date())
    val storageAccount = CloudStorageAccount.parse(connection)
    val sasToken = storageAccount.generateSharedAccessSignature(sas)
    sasToken
  }

  private def getDatePlusOneHour(): Date = {
    val time = LocalDateTime.now().plusHours(1)
    Date.from(time.atZone(ZoneId.systemDefault()).toInstant)
  }

  private def getDateMinusOneHour(): Date = {
    val time = LocalDateTime.now().minusHours(1)
    Date.from(time.atZone(ZoneId.systemDefault()).toInstant)
  }
}
