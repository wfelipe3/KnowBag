package knobag.architecture.chat.server.nio;

import com.google.inject.assistedinject.Assisted;
import knowbag.architecture.chat.executor.ConnectionExecutor;
import knowbag.architecture.chat.server.ChatServer;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.nio.channels.CompletionHandler;

/**
 * Created by feliperojas on 19/01/15.
 */
public class NioChatServer implements ChatServer {

    private AsynchronousServerSocketChannel channel;
    private ConnectionExecutor executor;
    private boolean running;

    @Inject
    public NioChatServer(@Assisted int port) {
        channel = createChanelInPort(port);
    }

    private AsynchronousServerSocketChannel createChanelInPort(int port) {
        try {
            return AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(port));
        } catch (IOException e) {
            throw new RuntimeException("Error creating channel in port " + port);
        }
    }

    @Override
    public void startServer() {
        while (true) {
            channel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                @Override
                public void completed(AsynchronousSocketChannel clientSocket, Object attachment) {
                    if ((clientSocket != null) && (clientSocket.isOpen())) {
                        try (InputStream is = Channels.newInputStream(clientSocket);
                             BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        ) {

                            while (true) {
                                Object object = br.readLine();
                                System.out.println("Received :" + object);
                                if (object.equals("EOF")) {
                                    clientSocket.close();
                                    break;
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("aca ocurrio un error ");
                }
            });
        }
    }
}
