package com.knowbag.codecompile;

import java.io.IOException;
import java.nio.file.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by feliperojas on 3/17/16.
 */
public class FileWatcherBuilder {

    private BiConsumer<Path, WatchEvent.Kind> fileSubscriber = (p, w) -> {
        throw new RuntimeException("fileSubscriver not defined");
    };

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
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get(projectFolder))) {
            toStream(ds)
                    .filter(p -> p.toFile().isDirectory())
                    .forEach(this::watch);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void watch(Path p) {
        FileWatcherBuilder
                .newFileWatcherForProject(p.toString())
                .withFolderTraverser(traverser)
                .create();
    }

    private Stream<Path> toStream(DirectoryStream<Path> ds) {
        return StreamSupport.stream(ds.spliterator(), true);
    }
}
