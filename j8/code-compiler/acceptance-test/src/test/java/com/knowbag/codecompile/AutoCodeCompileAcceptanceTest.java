package com.knowbag.codecompile;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Created by feliperojas on 3/17/16.
 */
public class AutoCodeCompileAcceptanceTest {

    private static final String TEST_PROJECT_FOLDER = "/Users/feliperojas/projects";

    @Before
    public void init() throws IOException {
        deleteAll(Paths.get(String.format("%s/testProject", TEST_PROJECT_FOLDER)));
        Files.deleteIfExists(Paths.get(String.format("%s/projects.comp", TEST_PROJECT_FOLDER)));
        Files.createDirectory(Paths.get(String.format("%s/testProject", TEST_PROJECT_FOLDER)));
        Files.write(Paths.get(String.format("%s/testProject/pom.xml", TEST_PROJECT_FOLDER)), "test project".getBytes(), CREATE_NEW);
    }

    @Test
    public void givenProjectFolderWithCodeWhenANewFileIsCreatedMarkTheProjectForCompilation() throws Exception {
        CompletableFuture.runAsync(this::startAutoCompiler);
        TimeUnit.SECONDS.sleep(1);
        Files.write(Paths.get(String.format("%s/testProject/HelloWorld.java", TEST_PROJECT_FOLDER)), "public class HelloWorld{}".getBytes(), CREATE_NEW);
        TimeUnit.SECONDS.sleep(10);
        List<String> lines = Files.readAllLines(Paths.get(String.format("%s/projects.comp", TEST_PROJECT_FOLDER)));
        Assertions.assertThat(lines).containsExactly("testProject");
    }

    private void startAutoCompiler() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", String.format("java -jar code-compiler-all-1.0-SNAPSHOT.jar -p %s/testProject -c %s/projects.comp", TEST_PROJECT_FOLDER, TEST_PROJECT_FOLDER));
            processBuilder.directory(new File("/Users/feliperojas/projects/git/KnowBag/j8/code-compiler/acceptance-test/src/test/resources"));
            Process process = processBuilder.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;

            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void destroy() throws IOException {
        deleteAll(Paths.get(String.format("%s/testProject", TEST_PROJECT_FOLDER)));
    }

    private void deleteAll(Path projectFolder) {
        if (Files.exists(projectFolder))
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(projectFolder)) {
                toStream(ds)
                        .forEach(p -> {
                            try {
                                if (Files.isDirectory(p)) {
                                    deleteAll(p);
                                } else {
                                    Files.deleteIfExists(p);
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                Files.delete(projectFolder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    private Stream<Path> toStream(DirectoryStream<Path> ds) {
        return StreamSupport.stream(ds.spliterator(), true);
    }
}
