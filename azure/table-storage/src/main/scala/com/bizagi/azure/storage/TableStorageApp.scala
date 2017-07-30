package com.bizagi.azure.storage

import java.util.UUID
import java.util.concurrent.Executors

import com.microsoft.azure.storage.CloudStorageAccount
import com.microsoft.azure.storage.table.TableQuery.QueryComparisons
import com.microsoft.azure.storage.table.{CloudTable, TableBatchOperation, TableOperation, TableQuery}
import storage.Person

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.Try
import scalax.chart.api._

/**
  * Created by dev-williame on 4/7/17.
  */
object TableStorageApp extends App {

  val connectionStrig = "DefaultEndpointsProtocol=https;AccountName=felipesa;AccountKey=3QejsikK1NUjGSH18gvhPqL7j7WUpHhQjnoWK990wtH1w2FGZAmd2/nfVX27p9ByuwKX7E+he6GXt+v3EOudmA==;EndpointSuffix=core.windows.net"

  measureAndPlot(iterations = 10, entities = 150000)(measureWithDifferentPartitionKey)

  private def measureAndPlot(iterations: Int, entities: Int)(f: (Int, Int) => Try[Seq[Long]]): Unit = {
    f(iterations, entities)
      .map(data => plot(iterations, entities, data))
      .recover {
        case e: Exception =>
          e.printStackTrace()
      }
  }

  private def measureWithSamePartitionKey(iterations: Int, entities: Int) = {
    measure(iterations, entities)(t => batchInsertEntities(t, entities))
  }

  private def measureWithDifferentPartitionKey(iterations: Int, entities: Int) = {
    measure(iterations, entities)(t => insertEntities(t, entities)(() => UUID.randomUUID().toString))
  }

  private def plot(iterations: Int, entities: Int, data: Seq[Long]): Unit = {
    val x = (1 to iterations).map(_ * entities).map(_.toDouble)
    val chart = XYLineChart(x.zip(data))
    chart.saveAsPNG("/tmp/chart2.png")
  }

  private def measure(iterations: Int, entities: Int)(insert: CloudTable => Unit) = {
    getTable(connectionStrig, "felipeTable")
      //      .flatMap(t => deleteTable(t))
      //      .flatMap(t => getTable(connectionStrig, "felipeTable"))
      .map { t =>
      (1 to iterations).map(i => {
        insert(t)
        val (time, size) = getEntities(t)
        println(s"get entites time $time")
        (time, size)
      }).unzip._1
    }
  }

  private def deleteTable(table: CloudTable) = Try {
    println("delete table")
    table.deleteIfExists()
    table
  }

  private def getEntities(table: CloudTable): (Long, Int) = {
    println("get entities")
    val pfilter = TableQuery.generateFilterCondition("PartitionKey", QueryComparisons.NOT_EQUAL, "felipe")
    val partitionQuery = TableQuery.from(classOf[Person]).where(pfilter)

    val (time, size) = execAndMeasure(() => {
      Try(table.execute(partitionQuery))
        .map(p => p.asScala.size)
        .recover {
          case e: Exception =>
            e.printStackTrace()
            0
        }.get
    })
    println(s"------------------------ size is $size")
    (time, size)
  }

  private def insertEntities(table: CloudTable, size: Int)(f: () => String): Unit = {
    println("insert entities")
    val (t, _) = execAndMeasure(() => {
      val ex = ExecutionContext.fromExecutor(Executors.newWorkStealingPool(100))
      val fs = (1 to size).map { i =>
        Future {
          val person = new Person(s"felipe${f()}", s"calle 123$i", 123455 + i)
          val tableOp = TableOperation.insertOrReplace(person)
          val (t, _) = execAndMeasure(() => table.execute(tableOp))
        }(ex)
      }
      Await.ready(Future.sequence(fs.toList), Duration.Inf)
    })
  }

  private def batchInsertEntities(table: CloudTable, size: Int): Unit = {
    println("insert entities batch")
    val (t, _) = execAndMeasure(() => {
      val ex = ExecutionContext.fromExecutor(Executors.newWorkStealingPool(100))
      val (times, fs) = (1 to size).grouped(100).map { l =>
        l.foldLeft(new TableBatchOperation()) { (tb, i) =>
          tb.insertOrReplace(new Person(s"felipe", s"calle 123${i}", 12345 + i))
          tb
        }
      }.map { tb =>
        execAndMeasure(() => Future {
          table.execute(tb)
          println(s"insert $tb")
        }(ex))
      }.toList.unzip
      Await.ready(Future.sequence(fs), Duration.Inf)
    })
  }

  private def getTable(connectionString: String, tableName: String) = Try {
    println("get table")
    val storage = CloudStorageAccount.parse(connectionString)
    val tableClient = storage.createCloudTableClient()
    val table = tableClient.getTableReference(tableName)
    table.createIfNotExists()
    table
  }

  private def execAndMeasure[A](f: () => A): (Long, A) = {
    val start = System.currentTimeMillis()
    val a = f()
    val end = System.currentTimeMillis()
    (end - start, a)
  }
}
