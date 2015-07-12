package knowbag.infraestructure.scheduler

/**
 * Created by feliperojas on 5/1/15.
 */
class ConfiguredSchedulerConfig(config: Map[String, Any]) extends SchedulerConfig {

  val default = DefaultSchedulerConfig

  def getProperty[T](name: String): T = {
    config.getOrElse(name, default.getProperty(name)).asInstanceOf[T]
  }
}
