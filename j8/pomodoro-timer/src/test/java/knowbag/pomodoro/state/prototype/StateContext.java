package knowbag.pomodoro.state.prototype;

/**
 * Created by feliperojas on 29/03/15.
 */
public class StateContext {

    private PomodoroState nextState;

    public StateContext(PomodoroState nextState) {
        this.nextState = nextState;
    }

    public PomodoroState getNextState() {
        return nextState;
    }
}
