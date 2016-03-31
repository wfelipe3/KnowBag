package com.knowbag.codecompile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

/**
 * Created by feliperojas on 3/17/16.
 */
public class FileWatcherBuilder {

    private Consumer<Path> traverser = p -> {
        throw new RuntimeException("traverser not defined");
    };

    private final String projectFolder;

    public FileWatcherBuilder(String projectFolder) {
        this.projectFolder = projectFolder;
    }

    public static FileWatcherBuilder newFileWatcherForProject(String s) {
        return new FileWatcherBuilder(s);
    }

    public FileWatcherBuilder withFolderTraverser(Consumer<Path> consumer) {
        this.traverser = consumer;
        return this;
    }

    public void create() {
        traverser.accept(Paths.get(projectFolder));
        DirectoryStreamUtils.traverse(Paths.get(projectFolder), paths -> {
            paths.filter(p -> p.toFile().isDirectory()).forEach(this::watch);
        });
    }

    private void watch(Path p) {
        FileWatcherBuilder
                .newFileWatcherForProject(p.toString())
                .withFolderTraverser(traverser)
                .create();
    }

}
