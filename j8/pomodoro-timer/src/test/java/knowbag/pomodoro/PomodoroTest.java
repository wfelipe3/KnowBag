package knowbag.pomodoro;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by feliperojas on 26/03/15.
 */
public class PomodoroTest {

    @Test
    public void givenUser_WhenPomodoroIsStarted_ThenStartWorkPhase() throws SchedulerException, InterruptedException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("scheduler", scheduler);
        JobDetail jobDetail = JobBuilder.newJob(WorkPhaseJob.class).usingJobData(jobDataMap).build();
        Trigger trigger = TriggerBuilder.newTrigger().startAt(DateBuilder.futureDate(25, DateBuilder.IntervalUnit.SECOND)).build();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
        Thread.sleep(60000);
    }
}
