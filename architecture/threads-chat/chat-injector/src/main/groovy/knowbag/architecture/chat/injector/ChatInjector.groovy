package knowbag.architecture.chat.injector

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * Created by feliperojas on 7/01/15.
 */
class ChatInjector {

    private static ChatInjector instance
    private List<AbstractModule> modules
    private Injector injector

    private ChatInjector() {
        modules = []
    }

    synchronized static ChatInjector getInstance() {
        if (instance == null) {
            instance = new ChatInjector()
        }
        instance
    }

    synchronized ChatInjector withModule(AbstractModule module) {
        modules.add(module)
        return this
    }

    synchronized ChatInjector createInjectionTree() {
        injector = Guice.createInjector(modules)
        return this
    }

    synchronized public <T>T get(Class<T> classToGet) {
        return injector.getInstance(classToGet)
    }
}
