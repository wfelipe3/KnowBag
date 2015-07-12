import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by feliperojas on 6/01/15.
 */
public class TestClient {

    public static void main(String args[]) throws IOException, InterruptedException {
//        sendMessage("another message");
        readMessages();
    }

    private static void readMessages() throws IOException {
        Socket socket = new Socket("localhost", 3000);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println("type:read");
        writer.println("user:felipe");
        writer.println("");
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line;
        do {
            line = reader.readLine();
            System.out.println(line);
        } while (!line.equals(""));

        socket.close();
    }

    private static void sendMessage(String message) throws IOException, InterruptedException {
        for (int i = 0; i < 100; i++) {
            Socket socket = new Socket("localhost", 3000);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("type:write");
            writer.println("user:felipe");
            writer.println("message:" + message + i);
            writer.println("");
            socket.close();
        }
    }

}
