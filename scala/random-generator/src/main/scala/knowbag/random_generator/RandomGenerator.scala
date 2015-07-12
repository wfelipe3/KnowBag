package knowbag.random_generator

/**
 * Created by feliperojas on 25/04/15.
 */
trait RandomGenerator[+T] {

  self => // alias

  def generate: T

  def map[S](f: T => S): RandomGenerator[S] = new RandomGenerator[S] {
    def generate: S = f(self.generate)
  }

  def flatMap[S](f: T => RandomGenerator[S]): RandomGenerator[S] = new RandomGenerator[S] {
    def generate: S = f(self.generate).generate
  }
}
