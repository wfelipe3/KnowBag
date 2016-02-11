package knowbag.rest.console;

import spark.Spark;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

/**
 * Created by feliperojas on 10/20/15.
 */
public class Main {

    public static void main(String args[]) {
        Spark.post("chat", (req, res) -> {
            CompletableFuture.runAsync(() -> {
                PrintWriter writer = new PrintWriter(System.out, true);
                writer.println(req.body());
            });
            return "";
        });

        CompletableFuture.runAsync(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                System.out.println("printed" + line);
            }
        });

    }
}
