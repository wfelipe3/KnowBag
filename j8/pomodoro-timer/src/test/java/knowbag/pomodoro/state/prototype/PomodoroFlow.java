package knowbag.pomodoro.state.prototype;

/**
 * Created by feliperojas on 29/03/15.
 */
public class PomodoroFlow {

    private PomodoroState state;

    public PomodoroFlow() {
        state = new WorkState();
    }

    public void executeState() {
        state = state.execute(null).getNextState();
    }
}
