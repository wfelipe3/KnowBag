package knowbag.pomodoro;

import org.quartz.*;

import java.util.Date;

/**
 * Created by feliperojas on 26/03/15.
 */
public class RestPhaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            System.out.println("rest phase ended " + new Date());
            Scheduler scheduler = (Scheduler) context.get("scheduler");
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("scheduler", scheduler);
            JobDetail jobDetail = JobBuilder.newJob(WorkPhaseJob.class).usingJobData(jobDataMap).build();
            Trigger trigger = TriggerBuilder.newTrigger().startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.SECOND)).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
