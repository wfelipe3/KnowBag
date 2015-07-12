package knowbag.architecture.chat.server.module

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import knowbag.architecture.chat.executor.ConnectionExecutor
import knowbag.architecture.chat.server.ChatServer
import knowbag.architecture.chat.server.ChatServerFactory
import knowbag.architecture.chat.server.tcp.TcpChatServer
import knowbag.architecture.chat.server.tcp.ThreadPoolConnectionExecutor

/**
 * Created by feliperojas on 7/01/15.
 */
class TcpChatServerModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .implement(ChatServer.class, TcpChatServer.class)
                .build(ChatServerFactory.class));

        bind(ConnectionExecutor.class)
                .to(ThreadPoolConnectionExecutor.class)
    }
}
