package knowbag.concurrency.client.test

import knowbag.concurrency.client.ChatClient
import org.junit.Before
import org.junit.Test

import static org.hamcrest.MatcherAssert.assertThat

/**
 * Created by feliperojas on 11/12/14.
 */
class ChatClientTest {

    public static final int PORT = 3000
    Server server

    @Before
    void before() {
        server = new Server()
        Thread.start {
            synchronized (server) {
                server.startServer()
            }
        }
    }

    @Test
    void givenSendMessage_ThenServerShouldGetMessage() {
        synchronized (server) {
            ChatClient client = new ChatClient("localhost", PORT)
            def message = "This is a test"
            client.sendMessage(message)
            assertThat(server.getMessage(), is(message))
        }
    }


    private class Server {

        private String message

        synchronized void startServer() {
            try {
                ServerSocket server = new ServerSocket(PORT)
                Socket socket = server.accept()
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                message = br.readLine();
            } catch (Exception e) {
                e.printStackTrace()
            }
        }

        synchronized String getMessage() {
            message
        }
    }
}
