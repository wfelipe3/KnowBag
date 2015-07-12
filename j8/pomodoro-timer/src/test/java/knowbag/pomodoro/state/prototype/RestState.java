package knowbag.pomodoro.state.prototype;

import knowbag.pomodoro.tdd.state.prototype.scheduler.PomodoroScheduler;

/**
 * Created by feliperojas on 29/03/15.
 */
public class RestState implements PomodoroState {

    @Override
    public StateContext execute(PomodoroScheduler scheduler) {
        System.out.println("resting");
        return new StateContext(new WorkState());
    }
}
