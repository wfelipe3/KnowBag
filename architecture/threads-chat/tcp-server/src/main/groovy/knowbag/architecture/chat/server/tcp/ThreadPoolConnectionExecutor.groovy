package knowbag.architecture.chat.server.tcp

import knowbag.architecture.chat.executor.ConnectionExecutor

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by feliperojas on 7/01/15.
 */
class ThreadPoolConnectionExecutor implements ConnectionExecutor {

    private ExecutorService pool

    ThreadPoolConnectionExecutor() {
        pool = Executors.newFixedThreadPool(1000)
    }

    void execute(task) {
        pool.execute(task)
    }
}
