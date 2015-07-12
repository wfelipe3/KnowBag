package knowbag.concurrency.client

/**
 * Created by feliperojas on 11/12/14.
 */
class MessageWriter {

    private PrintWriter writer

    MessageWriter(outputStream) {
        writer = new PrintWriter(outputStream)
    }

    void write(message) {
        writer.print(message)
    }

}
