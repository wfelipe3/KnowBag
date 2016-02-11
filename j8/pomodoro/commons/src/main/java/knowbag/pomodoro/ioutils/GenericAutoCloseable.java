package knowbag.pomodoro.ioutils;

import java.util.function.Consumer;

/**
 * Created by feliperojas on 1/9/16.
 */
public class GenericAutoCloseable<T> implements AutoCloseable {

    private T t;
    private Consumer<T> closeConsumer;

    public static <T> GenericAutoCloseable<T> of(T t, Consumer<T> closeConsumer) {
        return new GenericAutoCloseable<>(t, closeConsumer);
    }

    private GenericAutoCloseable(T t, Consumer<T> closeConsumer) {
        this.t = t;
        this.closeConsumer = closeConsumer;
    }

    public T get() {
        return t;
    }

    @Override
    public void close() {
        closeConsumer.accept(t);
    }
}
