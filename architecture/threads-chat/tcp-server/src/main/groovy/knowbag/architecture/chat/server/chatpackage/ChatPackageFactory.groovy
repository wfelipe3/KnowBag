package knowbag.architecture.chat.server.chatpackage

/**
 * Created by feliperojas on 9/01/15.
 */
class ChatPackageFactory {

    static ChatPackage createChatPackage(tokens) {
        ChatPackage chatPackage;

        if (tokens.type == "read") {
            chatPackage = new ReadChatPackage(tokens.user)
        } else if (tokens.type == "write") {
            chatPackage = new WriteChatPackage(tokens.user, tokens.message)
        } else {
            throw new RuntimeException("error")
        }

        return chatPackage
    }
}
