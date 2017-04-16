package com.bizagi.fp

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by dev-williame on 3/8/17.
  */
class FunctorExample extends FreeSpec {

  "Functor example" in {
    println(ListFunctor.map(List(1,2,3,4,5))(_ * 2))
  }

  trait Functor[F[_]] {
    def map[A, B](fun: F[A])(f: A => B): F[B]
  }

  val ListFunctor = new Functor[List] {
    override def map[A, B](fun: List[A])(f: A => B): List[B] = fun.map(f)
  }

  val optionalFunctor = new Functor[Optional] {
    override def map[A, B](opt: Optional[A])(f: A => B) = opt.map(f)
  }

  val funcFunctor = new Functor[Function] {
    override def map[A, B](fun: A=>B)(f: A=>B) = fun.compose(f)
  }

}
