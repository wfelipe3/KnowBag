package com.knowbag.codecompile;

import com.github.davidmoten.rx.FileObservable;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Created by WilliamE on 31/03/2016.
 */
public class FileWatcher {

    public static FileWatcherBuilder getFileWatcherBuilder(ProjectParams params) {
        return FileWatcherBuilder
                .newFileWatcherForProject(params.getProjectFile())
                .withFolderTraverser(getObservableFolderTraverser(params));
    }

    private static Consumer<Path> getObservableFolderTraverser(ProjectParams params) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        return p -> CompletableFuture.runAsync(() -> getFileObserver(params, p), executorService);
    }

    private static void getFileObserver(ProjectParams params, Path p) {
        FileObservable
                .from(new File(p.toString()), ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY)
                .filter(w -> !w.context().toString().contains(".git"))
                .subscribe(w -> {
                    FileEventProcessor.processFileEvent(params, p, w);
                });
    }

}
