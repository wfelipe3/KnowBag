package knowbag.pomodoro.tdd.state.prototype;

import knowbag.pomodoro.state.prototype.PomodoroState;
import knowbag.pomodoro.tdd.state.prototype.scheduler.PomodoroScheduler;
import knowbag.pomodoro.tdd.state.prototype.state.WorkState;

/**
 * Created by feliperojas on 29/03/15.
 */
public class Pomodoro {

    private PomodoroScheduler scheduler;
    private PomodoroState state;

    public Pomodoro(PomodoroPublisher publisher, PomodoroScheduler scheduler) {
        this.state = new WorkState(publisher);
        this.scheduler = scheduler;
    }

    public void executeState() {
        state = state.execute(scheduler).getNextState();
    }
}

