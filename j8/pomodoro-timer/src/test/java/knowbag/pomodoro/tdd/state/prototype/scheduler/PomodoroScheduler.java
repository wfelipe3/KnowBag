package knowbag.pomodoro.tdd.state.prototype.scheduler;

import knowbag.pomodoro.tdd.state.prototype.Pomodoro;
import knowbag.pomodoro.tdd.state.prototype.PomodoroManager;
import knowbag.pomodoro.tdd.state.prototype.PomodoroPublisher;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by feliperojas on 30/03/15.
 */
public class PomodoroScheduler {

    private Pomodoro pomodoro;
    private Scheduler scheduler;

    public PomodoroScheduler(PomodoroPublisher publisher) {
        try {
            pomodoro = PomodoroManager.start(publisher, this);
            this.scheduler = new StdSchedulerFactory().getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void schedule(int seconds) {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("pomodoro", pomodoro);
            JobDetail jobDetail = JobBuilder.newJob(PomodoroJob.class).usingJobData(jobDataMap).build();
            Trigger trigger = TriggerBuilder.newTrigger().startAt(DateBuilder.futureDate(seconds, DateBuilder.IntervalUnit.SECOND)).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
