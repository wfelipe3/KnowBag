package knobag.architecture.chat.main

import knobag.architecture.chat.server.module.NioChatServerModule
import knowbag.architecture.chat.injector.ChatInjector
import knowbag.architecture.chat.server.ChatServerManager
import knowbag.architecture.chat.server.module.TcpChatServerModule

/**
 * Created by feliperojas on 7/01/15.
 */
class Main {

    static void main(String[] args) {
        ChatInjector.getInstance()
//                .withModule(new TcpChatServerModule())
                .withModule(new NioChatServerModule())
                .createInjectionTree()

        ChatServerManager.createServerInPort(3000).start()
    }
}
