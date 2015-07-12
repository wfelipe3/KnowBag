package knowbag.infraestructure.scheduler

/**
 * Created by feliperojas on 5/1/15.
 */
object DefaultSchedulerConfig extends SchedulerConfig{

  val default = Map("work" -> 25000, "rest" -> 5000)

  override def getProperty[T](name: String): T = default.get(name).get.asInstanceOf[T]
}
