package knowbag.architecture.chat.server.queue

/**
 * Created by feliperojas on 8/01/15.
 */
class MessageQueue {

    private static MessageQueue instance
    private Map messagePerUser

    private MessageQueue() {
        messagePerUser = [:]
    }

    synchronized static MessageQueue getIntance() {
        if (instance == null)
            instance = new MessageQueue()
        instance
    }

    synchronized void queueMessage(user, message) {
        if (!userHasMessages(user))
            messagePerUser[user] = []
        messagePerUser[user] << message
    }

    synchronized List getMessages(user) {
        List messages
        if (userHasMessages(user))
            messages = messagePerUser[user]
        else
            messages = []
        messagePerUser[user] = []
        return messages
    }

    private boolean userHasMessages(user) {
        messagePerUser.containsKey(user)
    }
}
