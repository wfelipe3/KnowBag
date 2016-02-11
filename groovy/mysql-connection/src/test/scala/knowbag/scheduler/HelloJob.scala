package knowbag.scheduler

import org.quartz.{JobExecutionContext, Job}

/**
 * Created by feliperojas on 10/21/15.
 */
class HelloJob extends Job {
  override def execute(context: JobExecutionContext): Unit = println("hello world")
}
