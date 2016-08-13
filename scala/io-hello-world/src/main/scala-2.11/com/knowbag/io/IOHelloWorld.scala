package com.knowbag.io

import scalaz._
import Scalaz._
import scalaz.effect.IO._
import scalaz.effect._

/**
  * Created by dev-williame on 8/1/16.
  */
object IOHelloWorld extends App {

  val action1 = for {
    _ <- putStrLn("hello, world")
  } yield ()

  val action2 = IO {
    val source = scala.io.Source.fromFile("test.txt")
    source.getLines().toStream
  }

  val program: IO[Unit] = for {
    line <- readLn
    _ <- putStrLn(line)
  } yield ()

  action1.unsafePerformIO()
  (program |+| program).unsafePerformIO()
}
