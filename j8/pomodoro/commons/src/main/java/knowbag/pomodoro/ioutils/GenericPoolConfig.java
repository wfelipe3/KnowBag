package knowbag.pomodoro.ioutils;

import java.util.concurrent.TimeUnit;

/**
 * Created by feliperojas on 1/9/16.
 */
public class GenericPoolConfig {

    private TimeUnit pollTimeUnit;
    private long pollTime;

    public GenericPoolConfig(long pollTime, TimeUnit pollTimeUnit) {
        this.pollTimeUnit = pollTimeUnit;
        this.pollTime = pollTime;
    }

    public static GenericPoolConfig withPollTimeout(int time, TimeUnit timeUnit) {
        return new GenericPoolConfig(time, timeUnit);
    }

    public long getPollTime() {
        return pollTime;
    }

    public TimeUnit getPollTimeUnit() {
        return pollTimeUnit;
    }
}
