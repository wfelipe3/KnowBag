package knowbag.pomodoro.tdd.state.prototype.scheduler;

import org.junit.Test;
import org.quartz.SchedulerException;

/**
 * Created by feliperojas on 29/03/15.
 */
public class PomodoroSchedulerTest {

    @Test
    public void testScheduler() throws InterruptedException, SchedulerException {
        PomodoroScheduler scheduler = new PomodoroScheduler(event -> System.out.println(event));
        scheduler.schedule(0);
        scheduler.start();
        Thread.sleep(60000);
    }
}
