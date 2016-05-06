package com.knowbag.codecompile;

import com.github.davidmoten.rx.FileObservable;
import com.knowbag.codecompile.event.EventProcessor;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Created by WilliamE on 31/03/2016.
 */
public class FileWatcher {

    public static FileWatcherBuilder getFileWatcherBuilder(EventProcessor eventProcessor, ProjectParams params) {
        return FileWatcherBuilder
                .newFileWatcherForProject(params.getProjectFile())
                .withFolderTraverser(getObservableFolderTraverser(eventProcessor));
    }

    private static Consumer<Path> getObservableFolderTraverser(EventProcessor eventProcessor) {
        return p -> CompletableFuture.runAsync(() -> getFileObserver(eventProcessor, p), Executors.newFixedThreadPool(100000000));
    }

    private static void getFileObserver(EventProcessor eventProcessor, Path root) {
        FileObservable
                .from(new File(root.toString()), ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY)
                .subscribe(event -> {
                    eventProcessor.processFileEvent(root, event);
                });
    }

}
