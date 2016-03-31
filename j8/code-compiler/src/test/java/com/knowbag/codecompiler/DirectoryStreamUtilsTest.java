package com.knowbag.codecompiler;

import com.knowbag.codecompile.DirectoryStreamUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by WilliamE on 31/03/2016.
 */
public class DirectoryStreamUtilsTest {


    @Test
    public void givenPathThenExecuteConsumerWithInternalPathsStream() throws Exception {
        Path testFolder = Files.createTempDirectory("test");
        Path folder1 = Files.createDirectory(Paths.get(testFolder.toString(), "folder1"));
        Path folder2 = Files.createDirectories(Paths.get(testFolder.toString(), "folder2"));
        Path file = Files.createFile(Paths.get(testFolder.toString(), "file.txt"));
        List<Path> paths = DirectoryStreamUtils.traverse(testFolder, (Function<Stream<Path>, List<Path>>) p -> p.collect(Collectors.toList()));
        Assertions.assertThat(paths).contains(folder1, folder2, file);
    }
}
