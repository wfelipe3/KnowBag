package knobag.architecture.chat.server.module

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import knobag.architecture.chat.server.nio.NioChatServer
import knowbag.architecture.chat.server.ChatServer
import knowbag.architecture.chat.server.ChatServerFactory

/**
 * Created by feliperojas on 19/01/15.
 */
class NioChatServerModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .implement(ChatServer.class, NioChatServer.class)
                .build(ChatServerFactory.class));
    }
}
