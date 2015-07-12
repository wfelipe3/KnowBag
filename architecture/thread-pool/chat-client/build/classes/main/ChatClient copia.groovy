package knowbag.concurrency.client

/**
 * Created by feliperojas on 11/12/14.
 */
class ChatClient {

    static void main(String[] args) {
        Socket socket = new Socket("localhost", 3000)
        PrintWriter writer = new PrintWriter(socket.getOutputStream())
        writer.print("esto es una prueba")
        writer.flush()
        def reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))

        def line = null
        while (line == null) {
            line = reader.readLine()
        }
        println(line)
    }

}
