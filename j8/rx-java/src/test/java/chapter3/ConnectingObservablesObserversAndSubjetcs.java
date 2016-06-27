package chapter3;

import org.junit.Test;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by WilliamE on 27/06/2016.
 */
public class ConnectingObservablesObserversAndSubjetcs {

    @Test
    public void justShouldCreateAnObserverForGivenValue() throws Exception {
        AtomicReference<String> value = new AtomicReference<>();
        Observable.just("test").subscribe(value::set);
        assertThat(value.get()).isEqualToIgnoringCase("test");

        StringBuilder builder = new StringBuilder();
        Observable.just("this", "is", "a", "test").subscribe(e -> builder.append(e).append(" "));
        assertThat(builder.toString().trim()).isEqualToIgnoringCase("this is a test");
    }

    @Test
    public void timerShouldCreateAnObservableInATimerFashion() throws Exception {
        List<Long> values = new ArrayList<>();
        Observable<Long> timer = Observable.timer(10, TimeUnit.MILLISECONDS);
        timer.subscribe(values::add);
        Thread.sleep(50);
        assertThat(values).containsExactly(0L);
    }

    @Test
    public void intervalShouldCreateAnObservable() throws Exception {
        javaslang.collection.List<Long> values = javaslang.collection.List.nil();
        AtomicReference<javaslang.collection.List<Long>> value = new AtomicReference<>(values);
        Observable.interval(10, TimeUnit.MILLISECONDS).subscribe(e -> value.set(value.get().append(e)));
        Thread.sleep(50);
        assertThat(value.get().toJavaList()).containsExactly(0L, 1L, 2L, 3L, 4L);
    }

    @Test
    public void errorShouldCreateAnObservableThatEntersOnErrorCallback() throws Exception {
        AtomicReference<String> value = new AtomicReference<>();
        Observable.error(new RuntimeException()).subscribe(System.out::println, e -> value.set("error"));
        assertThat(value.get()).isEqualTo("error");
    }
}
