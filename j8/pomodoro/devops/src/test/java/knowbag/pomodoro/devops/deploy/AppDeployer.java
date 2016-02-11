package knowbag.pomodoro.devops.deploy;

import com.github.davidmoten.rx.FileObservable;
import javaslang.control.Try;
import knowbag.pomodoro.ioutils.AsyncIOs;
import rx.Observable;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * Created by feliperojas on 1/7/16.
 */
class AppDeployer {

    private String path;
    private Optional<Process> value = Optional.empty();

    private AppDeployer(String path) {
        this.path = path;
    }

    public static AppDeployer newAppDeployerForPath(String path) {
        return new AppDeployer(path);
    }

    public AppDeployer start() throws IOException {
        Observable<WatchEvent<?>> observable = FileObservable.from(Paths.get(path).toFile(), ENTRY_CREATE, ENTRY_MODIFY);
        CompletableFuture.runAsync(() -> observable.subscribe(w -> {
            Try.run(() -> {
                Process p = Runtime.getRuntime().exec(MessageFormat.format("java -jar {0}", path));
                value = Optional.of(p);
                AsyncIOs.printAsync(p.getErrorStream());
                AsyncIOs.printAsync(p.getInputStream());
            });
        }));
        return this;
    }

    public void stop() {
        value.ifPresent(Process::destroy);
    }

}
