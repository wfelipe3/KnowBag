package knowbag.pomodoro.tdd.state.prototype;

import knowbag.pomodoro.tdd.state.prototype.scheduler.PomodoroScheduler;

/**
 * Created by feliperojas on 29/03/15.
 */
public class PomodoroManager {

    public static Pomodoro start(PomodoroPublisher publisher, PomodoroScheduler scheduler) {
        return new Pomodoro(publisher, scheduler);
    }
}
