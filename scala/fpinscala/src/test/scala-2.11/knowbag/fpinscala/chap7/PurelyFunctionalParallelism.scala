package knowbag.fpinscala.chap7

import java.util.concurrent._

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by feliperojas on 9/19/16.
  */
class PurelyFunctionalParallelism extends FlatSpec with Matchers {

  "count words" should "return the number of words in a list of paragraphs" in {
    countWords(List()) should be(0)
    countWords(List("this")) should be(1)
    countWords(List("this is test")) should be(3)
    countWords(List("this is test", "with more words")) should be(6)
  }


  def countWords(p: List[String]): Int = {
    import Par._
    val map1: Par[List[Int]] = parMap(p)(_.split(" ").length)
    val map3: Par[Int] = map(map1)(_.sum)
    Par.run(Executors.newFixedThreadPool(2))(map3).get()
  }

  type Par[A] = ExecutorService => Future[A]

  object Par {
    def unit[A](a: A): Par[A] = (e: ExecutorService) => UnitFuture(a)

    def lazyUnit[A](a: => A): Par[A] = fork(unit(a))

    // Exercise 7.4
    def asyncF[A, B](f: A => B): A => Par[B] = a => lazyUnit(f(a))

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

    def map[A, B](pa: Par[A])(f: A => B): Par[B] =
      map2(pa, unit(()))((a, _) => f(a))

    def sortPar(parList: Par[List[Int]]): Par[List[Int]] =
      map(parList)(_.sorted)

    // Exercise 7.5
    def sequence[A](lp: List[Par[A]]): Par[List[A]] =
      lp.foldRight[Par[List[A]]](unit(List()))((h, t) => map2(h, t)(_ :: _))

    def parMap[A, B](ps: List[A])(f: A => B): Par[List[B]] =
      sequence(ps.map(asyncF(f)))

    // Exercise 7.6
    def parFilter[A](as: List[A])(f: A => Boolean): Par[List[A]] = {
      val s = sequence(as.map(asyncF(a => if (f(a)) List(a) else List())))
      map(s)(_.flatten)
    }

    def equal[A](e: ExecutorService)(p: Par[A], p2: Par[A]): Boolean =
      p(e).get() == p2(e).get()

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
