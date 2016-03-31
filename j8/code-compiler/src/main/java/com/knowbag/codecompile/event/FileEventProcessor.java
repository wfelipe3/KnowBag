package com.knowbag.codecompile.event;

import com.knowbag.codecompile.FileWatcher;
import com.knowbag.codecompile.ProjectParams;
import com.knowbag.codecompile.util.DirectoryStreamUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.stream.Stream;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

/**
 * Created by WilliamE on 31/03/2016.
 */
public class FileEventProcessor implements EventProcessor {

    private CompileRepository compileRepository;

    public FileEventProcessor(CompileRepository compileRepository) {
        this.compileRepository = compileRepository;
    }

    @Override
    public void processFileEvent(Path root, WatchEvent<?> event) {
        Path completePath = getCompileEventPath(root, event);
        if (isNewFolder(event, completePath)) {
            watchNewFolder(completePath);
        }
        Path projectPath = findProjectPath(completePath.getParent());
        compileRepository.add(projectPath);
    }

    private Path getCompileEventPath(Path root, WatchEvent<?> event) {
        Path eventPath = (Path) event.context();
        return Paths.get(root.toString() + "/" + eventPath.toString());
    }

    private void watchNewFolder(Path completePath) {
        FileWatcher.getFileWatcherBuilder(new FileEventProcessor(compileRepository), new ProjectParams(completePath.toString()));
    }

    private boolean isNewFolder(WatchEvent<?> event, Path completePath) {
        return Files.isDirectory(completePath) && event.kind() == ENTRY_CREATE;
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
