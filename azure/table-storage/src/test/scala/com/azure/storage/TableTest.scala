package com.azure.storage

import java.util.UUID
import java.util.concurrent.Executors

import com.microsoft.azure.storage.CloudStorageAccount
import com.microsoft.azure.storage.table.TableQuery.QueryComparisons
import com.microsoft.azure.storage.table.{TableBatchOperation, TableOperation, TableQuery, TableServiceEntity}
import org.scalatest.{FreeSpec, Matchers}

import scala.util.Try
import collection.JavaConverters._
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scalax.chart.api._
import org.sameersingh.scalaplot.Implicits._
import storage.Person

/**
  * Created by dev-williame on 4/2/17.
  */
class TableTest extends FreeSpec with Matchers {

  val connectionStrig = "DefaultEndpointsProtocol=https;AccountName=felipesa;AccountKey=3QejsikK1NUjGSH18gvhPqL7j7WUpHhQjnoWK990wtH1w2FGZAmd2/nfVX27p9ByuwKX7E+he6GXt+v3EOudmA==;EndpointSuffix=core.windows.net"

  "test table storage insert" in {
    val (time, table) = execAndMeasure(() => getTable)
    println(s"createTable $time")
    val (t, _) = execAndMeasure(() => {
      val ex = ExecutionContext.fromExecutor(Executors.newWorkStealingPool(100))
      val fs = (1 to 150000).map { i =>
        Future {
          val person = new Person(s"felipe${UUID.randomUUID().toString}", s"calle 123$i", 123455 + i)
          val tableOp = TableOperation.insertOrReplace(person)
          val (t, _) = execAndMeasure(() => table.map(t => t.execute(tableOp)))
          println(s"$i: single time $t")
        }(ex)
      }
      Await.ready(Future.sequence(fs.toList), Duration.Inf)
    })
    println(s"total time $t")
  }

  "test batch insert" in {
    val (time, table) = execAndMeasure(() => getTable)

    val (t, _) = execAndMeasure(() => {
      val ex = ExecutionContext.fromExecutor(Executors.newWorkStealingPool(100))
      val (times, fs) = (1 to 150000).grouped(100).map { l =>
        l.foldLeft(new TableBatchOperation()) { (tb, i) =>
          tb.insertOrReplace(new Person(s"felipe", s"calle 123$i", 12345 + i))
          tb
        }
      }.map { tb =>
        val (t1, fs) = execAndMeasure(() => table.map(t => Future(t.execute(tb))(ex)))
        (t1, fs.get)
      }.toList.unzip
      Await.ready(Future.sequence(fs), Duration.Inf)
      times.foreach(println)
    })

    println(s"total time $t")
  }

  "test get all entities in table" in {
    val pfilter = TableQuery.generateFilterCondition("PartitionKey", QueryComparisons.EQUAL, "felipe")
    val partitionQuery = TableQuery.from(classOf[Person]).where(pfilter)

    val (t, r) = execAndMeasure(() => {
      getTable
        .map(t => t.execute(partitionQuery))
        .map(p => p.asScala.size)
        .recover {
          case e: Exception =>
            e.printStackTrace()
            0
        }
    })

    println(s"the time was $t and the result was $r")
  }

  "test delte all entities" in {
    val pfilter = TableQuery.generateFilterCondition("PartitionKey", QueryComparisons.NOT_EQUAL, "felipe")
    val partitionQuery = TableQuery.from(classOf[Person]).where(pfilter)

    val (t, r) = execAndMeasure(() => {
      getTable
        .map(t => (t, t.execute(partitionQuery)))
        .map(p => p._2.asScala.par.map(t => TableOperation.delete(t)).map(o => p._1.execute(o)))
        .recover {
          case e: Exception =>
            e.printStackTrace()
            0
        }
    })

    println(s"the time was $t and the result was $r")
  }

  "test plotting" in {
    val chart = XYLineChart(List((1, 2), (2, 3), (3, 4), (5, 6), (6, 7), (8, 9), (9, 10), (10, 11), (11, 12)))
    chart.saveAsPNG("/tmp/chart.png")
  }

  private def getTable = Try {
    val storage = CloudStorageAccount.parse(connectionStrig)
    val tableClient = storage.createCloudTableClient()
    val table = tableClient.getTableReference("felipetable")
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
