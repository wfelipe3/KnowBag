package knowbag.pomodoro.time;

/**
 * Created by feliperojas on 1/9/16.
 */
public class StopWatch {
    public static class StopWatchStart {

        private long start;

        private StopWatchStart(long start) {
            this.start = start;
        }

        public static StopWatchStart start() {
            return new StopWatchStart(System.nanoTime());
        }

        public StopWatchStop stop() {
            return new StopWatchStop(start, System.nanoTime());
        }

    }

    public static class StopWatchStop {
        private long start;
        private long stop;

        public StopWatchStop(long start, long stop) {
            this.start = start;
            this.stop = stop;
        }

        public Time time() {
            return new Time(stop - start);
        }
    }

    public enum TimeUnit {
        MILLIS, NANOS
    }

    public static class Time {

        private final long nanos;

        public Time(long nanos) {
            this.nanos = nanos;
        }

        public long in(TimeUnit timeUnit) {
            switch (timeUnit) {
                case NANOS:
                    return nanos;
                case MILLIS:
                    return nanos / 1000000L;
                default:
                    throw new AssertionError("");
            }
        }
    }
}
