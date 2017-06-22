package com.knowbag.poc

import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by dev-williame on 4/16/17.
  */
object SlickApp extends App {
  val users = TableQuery[User]

  val db = Database.forConfig("db.default")
  val setup = DBIO.seq(
    users.schema.create,
    users += (2, "felipe", "casa2"),
    users.result
  )

  try {
    val f = db.run(setup)
    f.failed.foreach(println)
    f.foreach(println)

    db.run(users.result).map(_.foreach {
      case (id, name, address) =>
        println(id, name, address)
    })
  } finally db.close
}

class User(tag: Tag) extends Table[(Int, String, String)](tag, "USER") {
  def id = column[Int]("id", O.PrimaryKey)
  def name = column[String]("name")
  def address = column[String]("address")
  def * = (id, name, address)
}
