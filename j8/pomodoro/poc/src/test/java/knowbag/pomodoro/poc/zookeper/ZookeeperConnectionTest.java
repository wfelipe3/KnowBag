package knowbag.pomodoro.poc.zookeper;

import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by feliperojas on 12/10/15.
 */
public class ZookeeperConnectionTest {

    @Test
    public void pingZookeeper() throws IOException, InterruptedException, TimeoutException, ExecutionException {
        Socket socket = newZookeeperConnection("192.168.99.100", 2181);
        CompletableFuture<String> pingReply = listenMessage(socket.getInputStream());
        sendMessage("ruok", socket.getOutputStream());
        try {
            assertThat(pingReply.get(1000, TimeUnit.MILLISECONDS), is("imok"));
        } finally {
            socket.close();
        }
    }

    private CompletableFuture<String> listenMessage(InputStream inputStream) {
        return CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("reading message");
                    return readLine(inputStream);
                });
    }

    private Socket newZookeeperConnection(String host, int port) throws IOException {
        return new Socket(host, port);
    }

    private void sendMessage(String message, OutputStream outputStream) throws IOException {
        System.out.println("sending message " + message);
        PrintWriter writer = new PrintWriter(outputStream, true);
        writer.println(message);
    }

    private String readLine(InputStream inputStream) {
        try {
            return new BufferedReader(new InputStreamReader(inputStream)).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
