package knowbag.pomodoro.tdd.state.prototype.scheduler;

import knowbag.pomodoro.tdd.state.prototype.Pomodoro;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by feliperojas on 30/03/15.
 */
public class PomodoroJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Pomodoro pomodoro = (Pomodoro) context.getMergedJobDataMap().get("pomodoro");
        pomodoro.executeState();
    }
}
