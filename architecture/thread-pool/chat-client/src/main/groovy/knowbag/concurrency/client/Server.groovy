package knowbag.concurrency.client

/**
 * Created by feliperojas on 12/12/14.
 */
class Server {

    static void main(String[] args) {
        ServerSocket serverSocket = new ServerSocket(3000)
        Socket socket = serverSocket.accept()

        def reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))

        def line = null
        while (line == null) {
            line = reader.readLine()
        }
        println(line)

        PrintWriter writer = new PrintWriter(socket.getOutputStream())
        writer.print("tonces")
        writer.flush()
    }
}
