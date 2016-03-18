package com.knowbag.codecompile;

import javaslang.control.Try;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private static final String JAR_DIRECTORY = "C:\\dev\\git\\KnowBag\\j8\\code-compiler\\acceptance-test\\src\\test\\resources";
    private static final String TEST_PROJECT_FOLDER = "c:/dev/git";
    public static final String COMP_FOLDER = String.format("%s/projects.comp", TEST_PROJECT_FOLDER);
    public static final String PROJECT_FOLDER = String.format("%s/testProject", TEST_PROJECT_FOLDER);

    private CompletableFuture<JarExecutorFactory.JarExecutor> future;

    @Before
    public void init() throws IOException {
        deleteAll(Paths.get(PROJECT_FOLDER));
        Files.deleteIfExists(Paths.get(COMP_FOLDER));
        Files.createDirectory(Paths.get(PROJECT_FOLDER));
        Files.write(Paths.get(String.format("%s/pom.xml", PROJECT_FOLDER)), "test project".getBytes(), CREATE_NEW);
        future = CompletableFuture.supplyAsync(this::startAutoCompiler);
    }

    @Test
    public void givenProjectFolderWithCodeWhenANewFileIsCreatedMarkTheProjectForCompilation() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        Files.write(Paths.get(String.format("%s/HelloWorld.java", PROJECT_FOLDER)), "public class HelloWorld{}".getBytes(), CREATE_NEW);
        TimeUnit.SECONDS.sleep(10);
        List<String> lines = Files.readAllLines(Paths.get(COMP_FOLDER)).stream().distinct().collect(Collectors.toList());
        assertThat(lines).containsExactly("testProject");
    }

    private JarExecutorFactory.JarExecutor startAutoCompiler() {
        JarExecutorFactory.JarExecutor jarExecutor = JarExecutorFactory.getJarExecutorForOs();
        jarExecutor.execute(JAR_DIRECTORY, String.format("java -jar code-compiler-all-1.0-SNAPSHOT.jar -p %s -c %s", PROJECT_FOLDER, COMP_FOLDER));
        return jarExecutor;
    }

    @After
    public void destroy() throws IOException, ExecutionException, InterruptedException {
        JarExecutorFactory.JarExecutor jarExecutor = future.get();
        jarExecutor.stop();
        deleteAll(Paths.get(PROJECT_FOLDER));
        Files.deleteIfExists(Paths.get(COMP_FOLDER));
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
                case "os":
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
                process.destroyForcibly();
            }
        }
    }
}
