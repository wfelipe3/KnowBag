package knowbag.pomodoro.poc.rxjava;

import com.github.davidmoten.rx.FileObservable;
import javaslang.control.Try;
import knowbag.pomodoro.ioutils.AsyncIOs;
import org.junit.Test;
import rx.Observable;

import java.nio.file.*;
import java.util.concurrent.CompletableFuture;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardWatchEventKinds.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by feliperojas on 1/5/16.
 */
public class FileIOTest {


    private static final Object SYNC = new Object();
    private static final Path PATH = Paths.get("/Users/feliperojas/KnowBag/j8/pomodoro/poc/src/test/resources/file.txt");
    private boolean error = false;
    private boolean executed = false;
    private String message = "the file is not being watched";

    @Test
    public void testWatchFolderReactive() throws InterruptedException {
        Observable<WatchEvent<?>> observable = FileObservable
                .from(PATH.toFile(), ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);

        CompletableFuture.supplyAsync(() -> observable.subscribe(w -> {
            Path context = (Path) w.context();
            assertThat(context.getFileName().toString(), is("file.txt"));
            assertThat(w.kind(), is(ENTRY_MODIFY));
            executed = true;
            endTest();
        }, e -> {
            error = true;
            message = e.getMessage();
            endTest();
        }));

        CompletableFuture.runAsync(() -> Try.run(() -> Files.write(PATH, "this is a test".getBytes(), APPEND)));

        waitForTest();
        if (error || !executed) {
            fail(message);
        }
    }

    @Test
    public void testStopAndStartModifiedJarFile() {
        Observable<WatchEvent<?>> observable = FileObservable
                .from(Paths.get("/Users/feliperojas/KnowBag/j8/pomodoro/poc/src/test/resources/core-all-1.0-SNAPSHOT.jar").toFile(), ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);

        observable.subscribe(w -> {
            System.out.println(w.context());
            System.out.println(w.kind());
            if (w.kind() == ENTRY_CREATE) {
                Try.run(() -> {
                    Process p = Runtime.getRuntime().exec("java -jar /Users/feliperojas/KnowBag/j8/pomodoro/poc/src/test/resources/core-all-1.0-SNAPSHOT.jar");
                    AsyncIOs.printAsync(p.getInputStream());
                    AsyncIOs.printAsync(p.getErrorStream());
                    Thread.sleep(10000);
                    p.destroy();
                });
            }
        });
    }

    private void endTest() {
        synchronized (SYNC) {
            SYNC.notify();
        }
    }

    private void waitForTest() throws InterruptedException {
        synchronized (SYNC) {
            SYNC.wait(30000);
        }
    }

}
