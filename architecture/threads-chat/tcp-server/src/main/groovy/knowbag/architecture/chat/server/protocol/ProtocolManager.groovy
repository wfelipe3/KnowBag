package knowbag.architecture.chat.server.protocol

import knowbag.architecture.chat.server.chatpackage.ChatPackage
import knowbag.architecture.chat.server.chatpackage.ChatPackageFactory

/**
 * Created by feliperojas on 8/01/15.
 */
class ProtocolManager {

    static ChatPackage getPackage(reader) {
        Map tokens = getTokens(reader)
        return ChatPackageFactory.createChatPackage(tokens)
    }

    private static Map getTokens(reader) {
        Map tokens = [:]
        String line

        while ((line = reader.readLine()) != "") {
            line.split(":").with {
                tokens[it[0]] = it[1]
            }
        }
        tokens
    }

}
