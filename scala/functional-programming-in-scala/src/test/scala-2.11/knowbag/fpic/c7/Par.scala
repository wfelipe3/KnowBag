package knowbag.fpic.c7

import java.util.concurrent.{Callable, TimeUnit, Future, ExecutorService}

/**
 * Created by feliperojas on 8/3/15.
 */
object Par {
  type Par[A] = ExecutorService => Future[A]

  def fork[A](p: => Par[A]): Par[A] =
    es => es.submit(new Callable[A] {
      override def call(): A = p(es).get
    })

  def map2[A, B, C](p1: Par[A], p2: Par[B])(timeout: Int = 0)(f: (A, B) => C): Par[C] =
    es => {
      val af = p1(es)
      val bf = p2(es)
      UnitFuture(f(af.get(timeout, TimeUnit.SECONDS), bf.get(timeout, TimeUnit.SECONDS)))
    }

  def unit[A](a: A): Par[A] = es => UnitFuture(a)

  def lazyUnit[A](a: => A): Par[A] = fork(unit(a))

  def run[A](es: ExecutorService)(a: Par[A]): Future[A] = a(es)

  def asyncf[A, B](f: A => B): A => Par[B] =
    a => {
      lazyUnit(f(a))
    }

  def map[A, B](pa: Par[A])(f: A => B): Par[B] =
    map2(pa, unit(()))((a: A, _) => f(a))

  def parMap[A, B](ps: List[A])(f: A => B): Par[List[B]] = fork {
    sequence(ps.map(asyncf(f)))
  }

  def sequence[A](ps: List[Par[A]]): Par[List[A]] = ???


  private case class UnitFuture[A](get: A) extends Future[A] {
    override def isCancelled: Boolean = false

    override def get(timeout: Long, unit: TimeUnit): A = get

    override def cancel(mayInterruptIfRunning: Boolean): Boolean = false

    override def isDone: Boolean = true
  }

}
