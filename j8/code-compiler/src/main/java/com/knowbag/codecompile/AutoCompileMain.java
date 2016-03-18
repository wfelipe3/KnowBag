package com.knowbag.codecompile;

import com.github.davidmoten.rx.FileObservable;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

        ProjectParams params = new ProjectParams(projectFile, compile);

        getFileWatcherBuilder(params).create();

        new CountDownLatch(1).await();
    }

    private static FileWatcherBuilder getFileWatcherBuilder(ProjectParams params) {
        return FileWatcherBuilder
                .newFileWatcherForProject(params.getProjectFile())
                .withFolderTraverser(getObservableFolderTraverser(params));
    }

    private static Consumer<Path> getObservableFolderTraverser(ProjectParams params) {
        ExecutorService executorService = Executors.newFixedThreadPool(10000000);
        return p -> CompletableFuture.runAsync(() -> getFileObserver(params, p), executorService);
    }

    private static void getFileObserver(ProjectParams params, Path p) {
        System.out.println("Adding watcher to " + p.toString());
        FileObservable
                .from(new File(p.toString()), ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY)
                .filter(w -> !w.context().toString().contains(".git"))
                .subscribe(w -> {
                    processFileEvent(params, p, w);
                });
    }

    private static void processFileEvent(ProjectParams params, Path p, WatchEvent<?> w) {
        try {
            Path path = (Path) w.context();
            Path completePath = Paths.get(p.toString() + "/" + path.toString());
            if (Files.isDirectory(path) && w.kind() == ENTRY_CREATE) {
                getFileWatcherBuilder(new ProjectParams(completePath.toString(), params.getCompile()));
            }
            System.out.println("the file " + path + " has event " + w.kind());
            Path projectPath = findProjectPath(completePath.getParent());
            Files.write(params.getCompile(), (projectPath.getFileName().toString() + "\n").getBytes(), APPEND, CREATE);
            System.out.println(projectPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path findProjectPath(Path path) {
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
            System.out.println("searching pom in " + path);
            if (hasPomFile(toStream(ds))) {
                System.out.println("found pom  in " + path);
                return path;
            }
            else
                return findProjectPath(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static Stream<Path> toStream(DirectoryStream<Path> ds) {
        return StreamSupport.stream(ds.spliterator(), false);
    }

    private static boolean hasPomFile(Stream<Path> stream) {
        return stream.anyMatch(p -> p.getFileName().toString().equals("pom.xml"));
    }

    private static CommandLine parseCommands(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption(new Option("p", true, "project folder"));
        options.addOption(new Option("c", true, "compile projects output"));
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }
}
