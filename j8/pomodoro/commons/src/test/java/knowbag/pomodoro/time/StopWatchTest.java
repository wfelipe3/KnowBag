package knowbag.pomodoro.time;

import org.junit.Test;

import static knowbag.pomodoro.time.StopWatch.TimeUnit.MILLIS;
import static knowbag.pomodoro.time.StopWatch.TimeUnit.NANOS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

/**
 * Created by feliperojas on 1/9/16.
 */
public class StopWatchTest {

    public static final long ONE_SECOND_IN_NANOS = 1000000000L;
    public static final long THEN_SECONDS_IN_MILLIS = 10000000L;
    public static final long ZERO_TIME = 0L;

    @Test
    public void givenStopWatch_WhenStartsAndStops_ThenGiveTimeInMillis() {
        StopWatch.StopWatchStart stopWatch = StopWatch.StopWatchStart.start();
        StopWatch.StopWatchStop stop = stopWatch.stop();
        assertThat(stop.time().in(NANOS), is(greaterThan(ZERO_TIME)));
    }

    @Test
    public void givenStopWatch_WhenThereIsASleepOfOneSecond_ThenGiveTimeShouldReturnOneSecondTime() throws InterruptedException {
        StopWatch.StopWatchStart watch = StopWatch.StopWatchStart.start();
        Thread.sleep(1000);
        StopWatch.StopWatchStop stop = watch.stop();
        assertThat(stop.time().in(NANOS), is(greaterThan(ONE_SECOND_IN_NANOS)));
        assertThat(stop.time().in(MILLIS), is(lessThan(THEN_SECONDS_IN_MILLIS)));
    }

}
