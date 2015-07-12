package knowbag.posa.pipe_filter

/**
 * Created by feliperojas on 22/04/15.
 */
object Main extends App{

  val in = new PipeImpl[Int]
  val out = new PipeImpl[String]

  val generator = new ExampleGenerator(in)
  val filter = new ExampleFilter(in, out)
  val sink = new ExampleSink(out)

  generator.start()
  filter.start()
  sink.start()

  println("runner finished")
}
