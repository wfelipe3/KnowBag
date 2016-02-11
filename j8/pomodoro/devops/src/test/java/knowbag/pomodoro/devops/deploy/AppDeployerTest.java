package knowbag.pomodoro.devops.deploy;

import com.sun.jersey.api.client.Client;
import javaslang.control.Try;
import knowbag.pomodoro.ioutils.GenericAutoCloseable;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by feliperojas on 1/6/16.
 */
public class AppDeployerTest {

    private static final String DEPLOY_JAR_PATH = "/Users/feliperojas/KnowBag/j8/pomodoro/devops/src/test/resources/deploy/devops-all-1.0-SNAPSHOT.jar";
    private static final String JAR_PATH = "/Users/feliperojas/KnowBag/j8/pomodoro/devops/src/test/resources/jar/devops-all-1.0-SNAPSHOT.jar";
    private static final String PING_URL = "http://localhost:6789/ping";

    @Test
    public void givenEmptyFolderWhenAJarIsCopiedThenDeployJar() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        try {
            AppDeployer appDeployer = AppDeployer.newAppDeployerForPath(DEPLOY_JAR_PATH).start();
            CompletableFuture<String> ping =
                    CompletableFuture.runAsync(() -> copyJar(JAR_PATH, DEPLOY_JAR_PATH))
                            .thenAccept(v -> waitFor(15000))
                            .thenApply(v -> invokePingRestIn(PING_URL));
            assertThat(ping.get(), is("reply"));
            appDeployer.stop();
        } finally {
            deleteJarInPath(DEPLOY_JAR_PATH);
        }
    }

    @Test
    public void givenFolderWithJar_WhenANewJarIsCopiedAreNoJarIsRunning_ThenDeployNewJar() throws IOException, ExecutionException, InterruptedException {
        AppDeployer appDeployer = AppDeployer.newAppDeployerForPath("/Users/feliperojas/KnowBag/j8/pomodoro/devops/src/test/resources/deployWithJar/devops-all-1.0-SNAPSHOT.jar").start();
        CompletableFuture<String> ping =
                CompletableFuture.runAsync(() -> copyJar(JAR_PATH, "/Users/feliperojas/KnowBag/j8/pomodoro/devops/src/test/resources/deployWithJar/devops-all-1.0-SNAPSHOT.jar"))
                        .thenAccept(v -> waitFor(15000))
                        .thenApply(v -> invokePingRestIn(PING_URL));
        assertThat(ping.get(), is("reply"));
        appDeployer.stop();
    }

    private void deleteJarInPath(String deployJarPath) throws IOException {
        Path path = Paths.get(deployJarPath);
        if (Files.exists(path))
            Files.delete(path);
    }

    private String invokePingRestIn(String url) {
        try (GenericAutoCloseable<Client> client = GenericAutoCloseable.of(Client.create(), Client::destroy)) {
            return client.get().resource(url).get(String.class);
        }
    }

    private Try<Void> waitFor(int millis) {
        return Try.run(() -> Thread.sleep(millis));
    }

    private Try<Void> copyJar(String from, String to) {
        return Try.run(() -> Runtime.getRuntime().exec(MessageFormat.format("cp {0} {1}", from, to)));
    }
}
