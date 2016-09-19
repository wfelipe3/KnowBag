package knowbag.fpinscala.chap7

import java.util.concurrent.{Callable, ExecutorService, Future, TimeUnit}

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by feliperojas on 9/19/16.
  */
class PurelyFunctionalParallelism extends FlatSpec with Matchers {

  type Par[A] = ExecutorService => Future[A]


  object Par {
    def unit[A](a: A): Par[A] = (e: ExecutorService) => UnitFuture(a)

    def lazyUnit[A](a: => A): Par[A] = fork(unit(a))

    def map2[A, B, C](pa: Par[A], pb: Par[B])(f: (A, B) => C): Par[C] =
      (es: ExecutorService) => {
        val fa = pa(es)
        val fb = pb(es)
        UnitFuture(f(fa.get(), fb.get()))
      }

    def fork[A](a: => Par[A]): Par[A] =
      (es: ExecutorService) => {
        es.submit(new Callable[A] {
          override def call(): A = a(es).get()
        })
      }

    def run[A](e: ExecutorService)(pa: Par[A]): Future[A] = pa(e)
  }


  case class UnitFuture[A](a: A) extends Future[A] {
    override def isCancelled: Boolean = false

    override def get(): A = a

    override def get(timeout: Long, unit: TimeUnit): A = get

    override def cancel(mayInterruptIfRunning: Boolean): Boolean = false

    override def isDone: Boolean = true
  }

}
