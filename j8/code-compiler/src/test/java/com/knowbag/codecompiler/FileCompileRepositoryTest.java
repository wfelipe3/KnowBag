package com.knowbag.codecompiler;

import com.knowbag.codecompile.event.CompileRepository;
import com.knowbag.codecompile.event.FileCompileRepository;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by WilliamE on 31/03/2016.
 */
public class FileCompileRepositoryTest {

    @Test
    public void givenSingleProjectToAddThenWriteToCompFileWithTimestamp() throws Exception {
        Path testDirectory = Files.createTempDirectory("test");
        Path compFile = Files.createFile(Paths.get(testDirectory.toString(), "projects.comp"));
        CompileRepository compileRepository = new FileCompileRepository(compFile);
        String timestamp = compileRepository.add("EntityManager");
        List<String> projects = Files.readAllLines(compFile);
        assertThat(projects).containsExactly(getProjectLine(timestamp, "EntityManager"));
    }

    @Test
    public void givenMultipleProjectsToAddThenWriteToCompFileWithTimestamp() throws Exception {
        Path testDirectory = Files.createTempDirectory("test");
        Path compFile = Files.createFile(Paths.get(testDirectory.toString(), "projects.comp"));
        CompileRepository compileRepository = new FileCompileRepository(compFile);
        String timestamp1 = compileRepository.add("EntityManager");
        String timestamp2 = compileRepository.add("jpa");
        List<String> projects = Files.readAllLines(compFile);
        assertThat(projects).containsExactly(getProjectLine(timestamp1, "EntityManager"), getProjectLine(timestamp2, "jpa"));
    }

    private String getProjectLine(String timestamp1, String project) {
        return String.format("%s:[%s]", timestamp1, project);
    }
}

