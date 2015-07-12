package knowbag.concurrency.client

/**
 * Created by feliperojas on 11/12/14.
 */
class MessageReader {

    private BufferedReader reader

    MessageReader(inputStream) {
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    String read() {
        reader.readLine();
    }

}
