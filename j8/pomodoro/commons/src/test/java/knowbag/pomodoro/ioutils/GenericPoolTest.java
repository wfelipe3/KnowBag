package knowbag.pomodoro.ioutils;

import com.sun.jersey.api.client.Client;
import javaslang.control.Try;
import knowbag.pomodoro.time.StopWatch;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static knowbag.pomodoro.time.StopWatch.TimeUnit.MILLIS;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by feliperojas on 1/7/16.
 */
public class GenericPoolTest {

    @Test
    public void getClientFromPool() {
        GenericPool<Client> pool = getClientPoolWithSize(2);
        pool.executeWithinPool(c1 ->
                pool.executeWithinPool(c2 ->
                        assertThat(c1, is(not(c2)))));
    }

    @Test(expected = GenericPool.BusyPoolException.class)
    public void getClientFromPoolWhenThePoolSizeIsSmallerThanTheNumberOfRequestThrowException() {
        GenericPool<Client> pool = getClientPoolWithSize(1);
        pool.executeWithinPool(c ->
                pool.executeWithinPool(c2 ->
                        fail("this should throw exception")));
    }

    @Test
    public void get4TimesWithPoolWithSize2ShouldReuseClients() {
        List<Client> list = new ArrayList<>(2);
        GenericPool<Client> pool = getClientPoolWithSize(1);
        pool.executeWithinPool(list::add);
        pool.executeWithinPool(list::add);
        assertThat(list.get(0), is(list.get(1)));
    }

    @Test
    public void givenExecutionWithExceptionWhenNewExecuteIsCalledThenCreateNewClientInPool() {
        GenericPool<Client> pool = getClientPoolWithSize(1);
        Try.run(() -> pool.executeWithinPool(c -> {
            throw new RuntimeException();
        }));
        pool.executeWithinPool(c -> Assert.assertThat(c, notNullValue()));
    }

    @Test
    public void givenPoolConfigWithTimeOutTwoSecondsThenTheTestShouldLastMoreThan2Seconds() {
        GenericPool<Client> pool = GenericPool.newPoolWithSize(0, GenericPoolConfig.withPollTimeout(2, TimeUnit.SECONDS), Client::new);
        StopWatch.StopWatchStop stop = executeWithinStopWatch(() -> Try.run(() -> pool.executeWithinPool(Client::toString)));
        assertThat(stop.time().in(MILLIS), is(greaterThan(2000L)));
    }

    @Test
    public void givenPoolConfigForThreadPoolThenCreatePoolForThreads() {
        GenericPool<Thread> pool = GenericPool.newPoolWithSize(1, Thread::new);
        pool.executeWithinPool(t -> assertThat(t, notNullValue()));
        GenericPool<Thread> pool2 = GenericPool.newPoolWithSize(0, GenericPoolConfig.withPollTimeout(2, TimeUnit.SECONDS), Thread::new);
        StopWatch.StopWatchStop stop = executeWithinStopWatch(() -> Try.run(() -> pool2.executeWithinPool(Thread::toString)));
        assertThat(stop.time().in(MILLIS), is(greaterThan(2000L)));
    }

    @Ignore
    @Test
    public void givenDestroyedClientThenCreateANewOne() {
        List<Client> list = new ArrayList<>(2);
        GenericPool<Client> pool = getClientPoolWithSize(1);
        pool.executeWithinPool(c -> {
            c.destroy();
            list.add(c);
        });
        pool.executeWithinPool(list::add);
        assertThat(list.get(0), is(not(list.get(1))));
    }

    private GenericPool<Client> getClientPoolWithSize(int size) {
        return GenericPool.newPoolWithSize(size, Client::create);
    }

    private StopWatch.StopWatchStop executeWithinStopWatch(Runnable runnable) {
        StopWatch.StopWatchStart start = StopWatch.StopWatchStart.start();
        runnable.run();
        return start.stop();
    }

}
