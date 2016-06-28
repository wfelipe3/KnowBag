package chapter3;

import javaslang.control.Try;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import rx.Observable;
import rx.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

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

    private static Void UNIT = null;

    @Test
    public void createShouldCreateACustomObservable() throws Exception {
        Observable<Object> observable = Observable.create(s -> {
            Try.run(() -> {
                s.onNext("start");
                s.onCompleted();
            }).recover(e -> {
                s.onError(e);
                return UNIT;
            });
        });

        observable.subscribe(e -> Assertions.assertThat(e).isEqualTo("start"),
                e -> Assertions.fail("should not throw exception"), () -> assertThat(true).isEqualTo(true));
    }

    @Test
    public void createWithObserverShouldInvokeObserverMethods() throws Exception {
        AtomicReference<javaslang.collection.List<String>> values = new AtomicReference<>(javaslang.collection.List.nil());
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onCompleted() {
                values.set(values.get().append("onCompleted"));
            }

            @Override
            public void onError(Throwable e) {
                values.set(values.get().append("onError"));
            }

            @Override
            public void onNext(Integer integer) {
                values.set(values.get().append(String.valueOf(integer)));
            }
        };
        Observable.just(1, 2, 3, 4, 5).subscribe(observer);
        assertThat(values.get().toJavaList()).containsExactly("1", "2", "3", "4", "5", "onCompleted");
    }

    @Test
    public void observersDoNotBlockCurrentThread() throws Exception {
        List<Object> values = new ArrayList<>();
        Observable.create(s -> Try.run(() -> CompletableFuture.runAsync(() -> {
            IntStream.range(0, 1000000).forEach(s::onNext);
            s.onCompleted();
        })).recover(e -> {
            s.onError(e);
            return UNIT;
        })).subscribe(values::add, System.err::println, () -> System.out.println("finished"));
        assertThat(values.size()).isLessThan(1000000);
    }
}
