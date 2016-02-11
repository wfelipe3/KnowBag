package knowbag.pomodoro.ioutils;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Created by feliperojas on 1/7/16.
 */
class GenericPool<T> {

    private BlockingQueue<T> queue = new LinkedBlockingDeque<>();
    private final GenericPoolConfig config;
    private Supplier<T> supplier;

    public GenericPool(int size, GenericPoolConfig config, Supplier<T> supplier) {
        IntStream.range(0, size).forEach(i -> queue.offer(supplier.get()));
        this.supplier = supplier;
        this.config = config;
    }

    public static <T> GenericPool<T> newPoolWithSize(int size, GenericPoolConfig config, Supplier<T> supplier) {
        return new GenericPool<>(size, config, supplier);
    }

    public static <T> GenericPool<T> newPoolWithSize(int size, Supplier<T> supplier) {
        return new GenericPool<>(size, GenericPoolConfig.withPollTimeout(1, TimeUnit.SECONDS), supplier);
    }

    public void executeWithinPool(Consumer<T> consumer) {
        try {
            tryExecuteWithPoolObject(consumer);
        } catch (BusyPoolException e) {
            throw e;
        } catch (InterruptedException | RuntimeException e) {
            createNewPoolObjectAndThrowException(e);
        }
    }

    private void tryExecuteWithPoolObject(Consumer<T> consumer) throws InterruptedException {
        Optional<T> client = Optional.ofNullable(queue.poll(config.getPollTime(), config.getPollTimeUnit()));
        consumer.accept(client.orElseThrow(BusyPoolException::new));
        client.ifPresent(c -> queue.offer(c));
    }

    private void createNewPoolObjectAndThrowException(Exception e) {
        queue.offer(supplier.get());
        throw new RuntimeException(e);
    }

    public static class BusyPoolException extends RuntimeException {
    }
}
