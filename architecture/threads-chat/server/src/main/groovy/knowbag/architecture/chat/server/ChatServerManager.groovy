package knowbag.architecture.chat.server

import knowbag.architecture.chat.injector.ChatInjector

/**
 * Created by feliperojas on 6/01/15.
 */
class ChatServerManager {

    private static ChatServerManager instance
    private ChatServer chatServer

    private ChatServerManager(int port) {
        chatServer = ChatInjector.getInstance().get(ChatServerFactory.class).getChatServer(port)
    }

    static ChatServerManager createServerInPort(port) {
        if (instance == null)
            instance = new ChatServerManager(port)
        return instance
    }

    void start() {
        chatServer.startServer()
    }
}

