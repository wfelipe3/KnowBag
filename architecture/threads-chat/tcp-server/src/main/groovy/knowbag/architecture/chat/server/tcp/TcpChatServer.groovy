package knowbag.architecture.chat.server.tcp

import com.google.inject.assistedinject.Assisted
import knowbag.architecture.chat.executor.ConnectionExecutor
import knowbag.architecture.chat.server.ChatServer
import knowbag.architecture.chat.server.chatpackage.ChatPackage
import knowbag.architecture.chat.server.protocol.ProtocolManager

import javax.inject.Inject

/**
 * Created by feliperojas on 7/01/15.
 */
class TcpChatServer implements ChatServer {

    private ConnectionExecutor executor
    private ServerSocket serverSocket
    private boolean running

    @Inject
    TcpChatServer(@Assisted int port, ConnectionExecutor executor) {
        serverSocket = new ServerSocket(port)
        this.executor = executor
        running = true
    }

    @Override
    void startServer() {
        acceptConnection { Socket socket ->
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ChatPackage chatPackage = ProtocolManager.getPackage(reader)
            chatPackage.executeAction(socket)
        }
    }

    private void acceptConnection(task) {
        while (running) {
            Socket socket = serverSocket.accept()
            executor.execute {
                task(socket)
            }
        }
    }

}
