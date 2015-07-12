package knowbag.architecture.chat.server
/**
 * Created by feliperojas on 7/01/15.
 */
public interface ChatServerFactory {

    ChatServer getChatServer(int port)
}