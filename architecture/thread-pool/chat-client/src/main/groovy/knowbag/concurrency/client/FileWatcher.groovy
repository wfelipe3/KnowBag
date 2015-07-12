package knowbag.concurrency.client

import java.nio.file.*

import static java.nio.file.StandardWatchEventKinds.*

/**
 * Created by feliperojas on 12/12/14.
 */
class FileWatcher {

    static void main(String[] args) {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        Path folder = Paths.get("/Users/feliperojas/KnowBag/architecture/thread-pool/chat-client/src/main/resources")
        Path file = Paths.get("/Users/feliperojas/KnowBag/architecture/thread-pool/chat-client/src/main/resources/ChatClient.groovy")
        RandomAccessFile randomAccessFile = new RandomAccessFile(file.toFile(), "r")
        randomAccessFile.seek(randomAccessFile.length())
        folder.register(watcher, ENTRY_MODIFY)

        for (; ;) {
            WatchKey key = watcher.take()
            key.pollEvents().each { event ->
                if (event != OVERFLOW) {
                    println randomAccessFile.readLine()
                }
            }
            key.reset()
        }

    }
}
