package knowbag.pomodoro.tdd.state.prototype.state;

import knowbag.pomodoro.state.prototype.PomodoroState;
import knowbag.pomodoro.state.prototype.StateContext;
import knowbag.pomodoro.tdd.state.prototype.PomodoroPublisher;
import knowbag.pomodoro.tdd.state.prototype.scheduler.PomodoroScheduler;

import java.util.Date;

/**
 * Created by feliperojas on 29/03/15.
 */
public class WorkState implements PomodoroState {

    private PomodoroPublisher publisher;

    public WorkState(PomodoroPublisher publisher) {
        this.publisher = publisher;
    }

    public StateContext execute(PomodoroScheduler scheduler) {
        publisher.publish("Work " + new Date());
        scheduler.schedule(25);
        return new StateContext(new RestState(publisher));
    }
}
