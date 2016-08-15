package com.knowbag

import java.io.File
import java.util

import com.typesafe.config.{Config, ConfigFactory}
import org.docopt.Docopt

import scala.collection.JavaConversions._

/**
  * Created by feliperojas on 7/25/16.
  */
object DocoptApp extends App {

  val doc =
    """
      |Usage:
      | perf-test <task>... --config CONFIG [--database DATABASE] [--server SERVER] [--profiler] [--simulation SIMULATION]
      |Options:
      | --database DATABASE, -d DATABASE          Database
      | --server SERVER, -s SERVER                Server
      | --profiler, -p                            Start server with profiler
      | --simulation SIMULATION, -i SIMULATION    Gatling simulation
      | --config CONFIG, -c CONFIG                Configuration file
    """.stripMargin

  val opts = new Docopt(doc)
    .withVersion("2.0")
    .parse(args: _*)

  val tasks = getOptAs[util.ArrayList[String]]("<task>").foldRight(List.empty[Task]) { (v, l) => Task(v) :: l }

  private val perfTest: PerfTest = createPerfTestFromOpt()

  tasks.foreach { task =>
    TaskExecutor.execute(task, perfTest)
  }

  def createPerfTestFromOpt() = {
    PerfTest(
      db = getOptAs[String]("--database"),
      server = getOptAs[String]("--server"),
      profiler = getOptAs[Boolean]("--profiler"),
      simulation = getOptAs[String]("--simulation"),
      config = ConfigFactory.parseFile(new File(getOptAs[String]("--config")))
    )
  }

  def getOptAs[A](key: String) = {
    opts.get(key).asInstanceOf[A]
  }

}

case class PerfTest(config: Config, db: String, server: String, profiler: Boolean, simulation: String)

case class Task(name: String)

object TaskExecutor {
  def execute(task: Task, perfTest: PerfTest) = task match {
    case Task("deploy") => println(s"execute deploy in ${perfTest.server} and db ${perfTest.db}")
    case Task("cleanDeploy") => println(s"execute deploy in ${perfTest.server} and db ${perfTest.db}")
    case Task("gatling") => println(s"execute gatlint scenario ${perfTest.simulation}")
    case Task("copyEar") => println(s"Send ear to server ${perfTest.server}")
    case Task("predeploy") => println(s"execute predeploy for db ${perfTest.db}")
  }
}
