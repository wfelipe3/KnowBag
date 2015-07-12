package knowbag.architecture.chat.server.chatpackage

import knowbag.architecture.chat.server.queue.MessageQueue

/**
 * Created by feliperojas on 8/01/15.
 */
class ReadChatPackage implements ChatPackage{

    private String user

    ReadChatPackage(String user) {
        this.user = user
    }

    @Override
    void executeAction(Socket socket) {
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
        List messages = MessageQueue.getIntance().getMessages(user)
        messages.each { message ->
            writer.println(message)
        }
        writer.println("")
    }
}
