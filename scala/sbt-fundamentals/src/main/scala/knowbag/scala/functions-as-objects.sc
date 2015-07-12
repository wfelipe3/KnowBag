object List {
  def apply() = new List
  def apply[A, B](x: A) : B
  def apply[A, B, C](x: A, y: B): C
}