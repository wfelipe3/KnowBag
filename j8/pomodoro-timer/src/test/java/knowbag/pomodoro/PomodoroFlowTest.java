package knowbag.pomodoro;

import knowbag.pomodoro.tdd.state.prototype.*;
import knowbag.pomodoro.tdd.state.prototype.scheduler.PomodoroScheduler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by feliperojas on 29/03/15.
 */
public class PomodoroFlowTest {

    private List<String> events;
    private PomodoroPublisher publisher;

    @Before
    public void init() {
        events = new ArrayList<>();
        publisher = getPublisher(events);
    }

    @Test
    public void givenPomodoroFlowStart_WhenItStarts_ThenTheFlowIsInWorkState() {
        Pomodoro pomodoro = createPomodoroAndExecuteStateNTimes(publisher, 1);
        assertExpectedEvents(Arrays.asList("Work"));
    }

    @Test
    public void givenPomodoroFlowIsInWorkState_WhenItStarts_ThenTheFlowIsInRestState() {
        Pomodoro pomodoro = createPomodoroAndExecuteStateNTimes(publisher, 2);
        assertExpectedEvents(Arrays.asList("Work", "Rest"));
    }

    @Test
    public void givenPomodoroFlowIsInRestState_WhenItEnds_ThenTheFlowIsInWorkState() {
        Pomodoro pomodoro = createPomodoroAndExecuteStateNTimes(publisher, 3);
        assertExpectedEvents(Arrays.asList("Work", "Rest", "Work"));
    }

    private void assertExpectedEvents(List<String> expectedEvents) {
        assertThat(events, equalTo(expectedEvents));
    }

    private Pomodoro createPomodoroAndExecuteStateNTimes(PomodoroPublisher publisher, int times) {
        Pomodoro pomodoro = PomodoroManager.start(publisher, new PomodoroScheduler(publisher));
        IntStream.range(0, times).forEach(i -> pomodoro.executeState());
        return pomodoro;
    }

    private PomodoroPublisher getPublisher(final List<String> events) {
        return (event) -> events.add(event);
    }
}
