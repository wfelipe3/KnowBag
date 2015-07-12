package knowbag.pomodoro.state.prototype;

import knowbag.pomodoro.tdd.state.prototype.scheduler.PomodoroScheduler;

/**
 * Created by feliperojas on 29/03/15.
 */
public interface PomodoroState {

    public StateContext execute(PomodoroScheduler scheduler);
}
