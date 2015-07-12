package knowbag.infraestructure.scheduler

/**
 * Created by feliperojas on 5/1/15.
 */
trait SchedulerConfig {
  def getProperty[T](name: String): T
}
