package com.knowbag.codecompile.event;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Created by WilliamE on 31/03/2016.
 */
public class FileCompileRepository implements CompileRepository {

    private Path compFile;

    public FileCompileRepository(Path compFile) {
        this.compFile = compFile;
    }

    @Override
    public String add(String projectPath) {
        try {
            long timestamp = System.currentTimeMillis();
            Files.write(compFile, String.format("%s:[%s]\n", timestamp, projectPath).getBytes(), CREATE, APPEND);
            return String.valueOf(timestamp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
