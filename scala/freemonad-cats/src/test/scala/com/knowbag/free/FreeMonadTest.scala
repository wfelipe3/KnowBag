package com.knowbag.free

import cats.free.Free
import cats.{Id, ~>}
import org.scalatest.{FreeSpec, Matchers}
import shapeless.{Coproduct, :+:, CNil}

/**
  * Created by feliperojas on 6/19/17.
  */
class FreeMonadTest extends FreeSpec with Matchers {

  "test free monad" in {
    import ActionFree._

    val f = for {
      d <- readData(123)
      t <- transformData(d)
      _ <- writeData(789, t)
    } yield ()

    val compiler: Action ~> Id = new (Action ~> Id) {
      override def apply[A](fa: Action[A]): Id[A] = fa match {
        case ReadData(_) => "test value".asInstanceOf[A]
        case TransformData(data) => s"t$data".asInstanceOf[A]
        case WriteData(port, data) => println(s"writing to port $port data $data").asInstanceOf[A]
      }
    }

    val result = f.foldMap(compiler)

  }

  sealed trait OtherAction[A]

  case class Other[A](a: Int) extends OtherAction[A]

  case class SomeOther[A](a: String) extends OtherAction[A]

  object OtherActionFree {
    def other[A](a: Int): Free[OtherAction, A] = Free.liftF(Other(a))

    def someOther[A](a: String): Free[OtherAction, A] = Free.liftF(SomeOther(a))
  }


  sealed trait Action[A]

  final case class ReadData(port: Int) extends Action[String]

  final case class TransformData(data: String) extends Action[String]

  final case class WriteData(port: Int, data: String) extends Action[Unit]

  object ActionFree {
    def readData(port: Int): Free[Action, String] = Free.liftF(ReadData(port))

    def transformData(data: String): Free[Action, String] = Free.liftF(TransformData(data))

    def writeData(port: Int, data: String): Free[Action, Unit] = Free.liftF(WriteData(port, data))
  }

}
