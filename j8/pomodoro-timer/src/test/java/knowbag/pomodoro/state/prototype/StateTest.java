package knowbag.pomodoro.state.prototype;

import org.junit.Test;

/**
 * Created by feliperojas on 29/03/15.
 */
public class StateTest {

    @Test
    public void testWorkingState() {
        PomodoroFlow pomodoroFlow = new PomodoroFlow();
        pomodoroFlow.executeState();
        pomodoroFlow.executeState();
        pomodoroFlow.executeState();
        pomodoroFlow.executeState();
        pomodoroFlow.executeState();
        pomodoroFlow.executeState();
    }
}
