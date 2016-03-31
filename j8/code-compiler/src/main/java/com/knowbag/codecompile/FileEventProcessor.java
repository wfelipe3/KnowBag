package com.knowbag.codecompile;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Created by WilliamE on 31/03/2016.
 */
public class FileEventProcessor {

    public static void processFileEvent(ProjectParams params, Path p, WatchEvent<?> w) {
        try {
            Path path = (Path) w.context();
            Path completePath = Paths.get(p.toString() + "/" + path.toString());
            if (Files.isDirectory(completePath) && w.kind() == ENTRY_CREATE) {
                FileWatcher.getFileWatcherBuilder(new ProjectParams(completePath.toString(), params.getCompile()));
            }
            Path projectPath = findProjectPath(completePath.getParent());
            Files.write(params.getCompile(), (projectPath.getFileName().toString() + "\n").getBytes(), APPEND, CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path findProjectPath(Path path) {
        return DirectoryStreamUtils.traverse(path, p -> {
            if (hasPomFile(p))
                return path;
            else
                return findProjectPath(path.getParent());
        });
    }

    private static boolean hasPomFile(Stream<Path> stream) {
        return stream.anyMatch(p -> p.getFileName().toString().equals("pom.xml"));
    }
}
