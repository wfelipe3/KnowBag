package knowbag.pomodoro.state.prototype;

import knowbag.pomodoro.tdd.state.prototype.scheduler.PomodoroScheduler;

/**
 * Created by feliperojas on 29/03/15.
 */
public class WorkState implements PomodoroState {

    public StateContext execute(PomodoroScheduler scheduler) {
        System.out.println("the system is in working state");
        return new StateContext(new RestState());
    }
}
