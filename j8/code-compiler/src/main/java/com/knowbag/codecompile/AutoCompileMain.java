package com.knowbag.codecompile;

import com.github.davidmoten.rx.FileObservable;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Created by feliperojas on 3/17/16.
 */
public class AutoCompileMain {

    public static void main(String args[]) throws ParseException, InterruptedException {
        CommandLine cmd = parseCommands(args);
        String projectFile = cmd.getOptionValue("p");
        Path compile = Paths.get(cmd.getOptionValue("c"));

        System.out.println("param p " + projectFile);
        System.out.println("param c " + compile);

        getFileWatcherBuilder(projectFile, compile).create();

        new CountDownLatch(1).await();
    }

    private static FileWatcherBuilder getFileWatcherBuilder(String projectFile, Path compile) {
        return FileWatcherBuilder
                .newFileWatcherForProject(projectFile)
                .withFolderTraverser(getObservableFolderTraverser(projectFile, compile));
    }

    private static Consumer<Path> getObservableFolderTraverser(String projectFile, Path compile) {
        return p -> CompletableFuture.runAsync(() -> getFileObserver(projectFile, compile, p));
    }

    private static void getFileObserver(String projectFile, Path compile, Path p) {
        System.out.println("Adding watcher to " + p.toString());
        FileObservable.
                from(new File(projectFile), ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY).
                subscribe(w -> {
                    try {
                        Path path = (Path) w.context();
                        if (Files.isDirectory(path) && w.kind() == ENTRY_CREATE) {
                            getFileWatcherBuilder(Paths.get(p.toString() + "/" + path.toString()).toString(), compile);
                        }
                        System.out.println("the file " + path + " has event " + w.kind());
                        Files.write(compile, Paths.get(p.toString() + "/" + path.toString()).getParent().getFileName().toString().getBytes(), APPEND, CREATE);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private static CommandLine parseCommands(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption(new Option("p", true, "project folder"));
        options.addOption(new Option("c", true, "compile projects output"));
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }
}
