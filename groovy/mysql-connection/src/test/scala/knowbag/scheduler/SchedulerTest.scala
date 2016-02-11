package knowbag.scheduler

import java.util.Date

import org.quartz.impl.StdSchedulerFactory
import org.quartz.{Job, JobBuilder, JobExecutionContext, TriggerBuilder}
import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 10/21/15.
 */
class SchedulerTest extends FlatSpec with Matchers {

  behavior of "akka scheduler"

  it should "execute job at scheduled time" in {
    val schedulerFactory = new StdSchedulerFactory()
    val scheduler = schedulerFactory.getScheduler
    val job = JobBuilder.newJob(classOf[HelloJob])
      .withIdentity("hello job", "group 1")
      .build()

    val trigger = TriggerBuilder.newTrigger()
      .withIdentity("trigger1", "group1")
      .startAt(new Date)
      .build()

    scheduler.scheduleJob(job, trigger)
    scheduler.start()

    Thread.sleep(90 * 1000)
    scheduler.shutdown(true)
  }

}
