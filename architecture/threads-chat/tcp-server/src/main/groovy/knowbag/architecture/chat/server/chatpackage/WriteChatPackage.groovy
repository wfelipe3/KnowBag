package knowbag.architecture.chat.server.chatpackage

import knowbag.architecture.chat.server.queue.MessageQueue

/**
 * Created by feliperojas on 8/01/15.
 */
class WriteChatPackage implements ChatPackage{

    private String user
    private String message

    WriteChatPackage(String user, String message) {
        this.user = user
        this.message = message
    }

    @Override
    void executeAction(Socket socket) {
        MessageQueue.getIntance().queueMessage(user, message)
    }
}
