package com.knowbag.codecompile;

import javaslang.control.Try;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by feliperojas on 3/17/16.
 */
public class AutoCodeCompileAcceptanceTest {

    private static final String JAR_DIRECTORY;

    static {
        String file = AutoCodeCompileAcceptanceTest.class.getClassLoader().getResource("code-compiler-all-1.0-SNAPSHOT.jar").getFile();
        String os = System.getProperty("os.name");
        switch (os) {
            case "Windows 8.1":
                JAR_DIRECTORY = Paths.get(file.substring(1)).getParent().toString();
                break;
            case "Mac OS X":
                JAR_DIRECTORY = Paths.get(file).getParent().toString();
                break;
            default:
                throw new AssertionError("");
        }
    }

    private CompletableFuture<JarExecutorFactory.JarExecutor> future;
    private Path projectFolder;
    private Path compFile;

    @Before
    public void init() throws IOException {
        projectFolder = Files.createTempDirectory("testProject");
        compFile = Files.createTempFile("projects", "comp");
        Files.write(Paths.get(projectFolder.toString(), "pom.xml"), "test".getBytes(), StandardOpenOption.CREATE_NEW);
    }

    @Test
    public void givenProjectFolderWithCodeWhenANewFileIsCreatedMarkTheProjectForCompilation() throws Exception {
        future = CompletableFuture.supplyAsync(this::startAutoCompiler);
        waitFor(1);
        Path hwPath = Paths.get(projectFolder.toString(), "HelloWorld.java");
        Files.write(hwPath, "public class HelloWorld{}".getBytes(), CREATE_NEW);
        waitFor(10);
        List<String> lines = Files.readAllLines(compFile).stream().distinct().collect(Collectors.toList());
        assertThat(lines).containsExactly(projectFolder.getFileName().toString());
    }

    @Test
    public void givenProjectWithPackagesWhenClassIsModifiedThenMarkTheProjectForCompilation() throws Exception {
        Path com = Files.createDirectory(Paths.get(projectFolder.toString(), "com"));
        Files.write(Paths.get(com.toString(), "HelloWorld.java"), "public class HellWorld {".getBytes(), StandardOpenOption.CREATE_NEW);
        future = CompletableFuture.supplyAsync(this::startAutoCompiler);
        waitFor(1);
        Files.write(Paths.get(com.toString(), "HelloWorld.java"), "public static void main(String args[]){}}".getBytes(), StandardOpenOption.APPEND);
        waitFor(10);
        List<String> lines = Files.readAllLines(compFile).stream().distinct().collect(Collectors.toList());
        assertThat(lines).containsExactly(projectFolder.getFileName().toString());
    }

    @Test
    public void whenANewFolderIsCreatedInsideAProjectThenTheNewFolderShouldBeWatched() throws Exception {
        Path com = Files.createDirectory(Paths.get(projectFolder.toString(), "com"));
        future = CompletableFuture.supplyAsync(this::startAutoCompiler);
        waitFor(1);
        Path foo = Files.createDirectories(Paths.get(com.toString(), "foo"));
        waitFor(1);
        Files.write(Paths.get(foo.toString(), "HelloWorld.java"), "public class HellWorld {".getBytes(), StandardOpenOption.CREATE_NEW);
        waitFor(10);
        List<String> lines = Files.readAllLines(compFile).stream().distinct().collect(Collectors.toList());
        assertThat(lines).containsExactly(projectFolder.getFileName().toString());
    }

    private void waitFor(int timeout) throws InterruptedException {
        TimeUnit.SECONDS.sleep(timeout);
    }

    private JarExecutorFactory.JarExecutor startAutoCompiler() {
        JarExecutorFactory.JarExecutor jarExecutor = JarExecutorFactory.getJarExecutorForOs();
        jarExecutor.execute(JAR_DIRECTORY, String.format("java -jar code-compiler-all-1.0-SNAPSHOT.jar -p %s -c %s", projectFolder.toString(), compFile.toString()));
        return jarExecutor;
    }

    @After
    public void destroy() throws IOException, ExecutionException, InterruptedException {
        stopJar(future);
        deleteAll(projectFolder);
        Files.deleteIfExists(compFile);
    }

    private void stopJar(CompletableFuture<JarExecutorFactory.JarExecutor> jar) throws InterruptedException, ExecutionException {
        jar.get().stop();
    }

    private void deleteAll(Path projectFolder) {
        if (Files.exists(projectFolder))
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(projectFolder)) {
                toStream(ds).forEach(this::deleteAccordingToType);
                Files.delete(projectFolder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    private void deleteAccordingToType(Path p) {
        try {
            if (Files.isDirectory(p)) {
                deleteAll(p);
            } else {
                Files.deleteIfExists(p);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<Path> toStream(DirectoryStream<Path> ds) {
        return StreamSupport.stream(ds.spliterator(), true);
    }

    public static class JarExecutorFactory {

        public static JarExecutor getJarExecutorForOs() {
            String os = System.getProperty("os.name");
            switch (os) {
                case "Windows 8.1":
                    return new JarExecutor("cmd", "/c");
                case "Mac OS X":
                    return new JarExecutor("/bin/bash", "-c");
                default:
                    throw new AssertionError("os not found");
            }
        }

        private static class JarExecutor {
            private final String bash;
            private final String type;
            private Process process;

            public JarExecutor(String bash, String type) {
                this.bash = bash;
                this.type = type;
            }

            public void execute(String directory, String command) {
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder(bash, type, command);
                    processBuilder.directory(new File(directory));
                    process = processBuilder.start();

                    CompletableFuture.runAsync(() -> Try.run(() -> {
                        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line = null;

                        while ((line = in.readLine()) != null) {
                            System.out.println(line);
                        }
                    }));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            public void stop() {
                process.destroy();
            }
        }
    }
}
