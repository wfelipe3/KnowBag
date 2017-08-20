package com.knowbag.azure

import java.io.FileOutputStream
import java.time.{LocalDateTime, ZoneId}
import java.util.Date
import java.util

import com.microsoft.azure.storage.{CloudStorageAccount, SharedAccessAccountPermissions, SharedAccessAccountPolicy, StorageCredentialsSharedAccessSignature}

import scala.util.Try
import com.microsoft.azure.storage.SharedAccessAccountPermissions._
import com.microsoft.azure.storage.SharedAccessAccountResourceType.{CONTAINER, OBJECT}
import com.microsoft.azure.storage.SharedAccessAccountService.{BLOB, TABLE}
import com.microsoft.azure.storage.SharedAccessProtocols.HTTPS_HTTP
import com.microsoft.azure.storage.blob.{SharedAccessBlobPermissions, SharedAccessBlobPolicy}

import scala.collection.JavaConverters._

/**
  * Created by dev-williame on 4/17/17.
  */
object DownloadFiles extends App {

  private val connection = "DefaultEndpointsProtocol=https;AccountName=scaleconf;AccountKey=Zl9MhlM/iiAiyIKdvqJvX3x3tWIEM0P5VfxYqRuQJbgxM7JMFlYnvOqq0YoWHLMSdwC36R/ogbqwcsk36ajDEw==;EndpointSuffix=core.windows.net"

  getContainer(connection)
    .map(c => (c, c.listBlobs().asScala))
    .map { cbl =>
      val (c, bl) = cbl
      bl.par
        .map(cb => cb.getUri.getPath.split("/").last)
        .foreach { cb =>
          c.getBlockBlobReference(cb).download(new FileOutputStream(s"/Users/dev-williame/scaleconf/$cb"))
        }
    }

  private def printErrorAndReturn[T](default: T): PartialFunction[Throwable, T] = {
    case e: Exception =>
      e.printStackTrace()
      default
  }

  private def getBlobContainerFromSAS(connection: String, storageAccount: String, container: String)(date: () => Date) =
    getSASToken(util.EnumSet.of(READ, WRITE, CREATE, UPDATE, ADD))(connection)(date)
      .flatMap(createSASAccount(storageAccount))
      .flatMap(blobContainerSas(container))

  private def blobContainerSas(container: String)(account: CloudStorageAccount) = Try {
    val bloblClient = account.createCloudBlobClient()
    bloblClient.getContainerReference(container)
  }

  private def createSASAccount(account: String)(sasToken: String) = Try {
    val accountSAS = new StorageCredentialsSharedAccessSignature(sasToken)
    new CloudStorageAccount(accountSAS, false, null, account)
  }

  private def getSASToken(permissions: util.EnumSet[SharedAccessAccountPermissions])(connection: String)(date: () => Date) = Try {
    val sas = new SharedAccessAccountPolicy
    sas.setPermissions(permissions)
    sas.setProtocols(HTTPS_HTTP)
    sas.setResourceTypes(util.EnumSet.of(CONTAINER, OBJECT))
    sas.setServices(util.EnumSet.of(BLOB, TABLE))
    sas.setSharedAccessExpiryTime(date())
    val storageAccount = CloudStorageAccount.parse(connection)
    val sasToken = storageAccount.generateSharedAccessSignature(sas)
    sasToken
  }

  private def getBlobSASToken(permissions: util.EnumSet[SharedAccessBlobPermissions])(connection: String)(date: () => Date) = Try {
    val sas = new SharedAccessBlobPolicy
    sas.setPermissions(permissions)
    sas.setSharedAccessStartTime(date())
    getContainer(connection)
      .map(c => c.getBlockBlobReference("test"))
      .map(b => b.generateSharedAccessSignature(sas, "test"))
  }

  private def getDatePlusOneHour(): Date = {
    val time = LocalDateTime.now().plusHours(1)
    Date.from(time.atZone(ZoneId.systemDefault()).toInstant)
  }

  private def getContainer(connection: String) = Try {
    val storageAccount = CloudStorageAccount.parse(connection)
    val blobClient = storageAccount.createCloudBlobClient
    blobClient.getContainerReference("scaleconf")
  }
}
