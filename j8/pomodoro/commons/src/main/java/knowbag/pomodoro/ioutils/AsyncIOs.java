package knowbag.pomodoro.ioutils;

import javaslang.control.Try;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Created by feliperojas on 1/9/16.
 */
public class AsyncIOs {

    public static void printAsync(InputStream inputStream) {
        CompletableFuture.runAsync(() -> Try.run(() -> {
            BufferedReader bri = new BufferedReader
                    (new InputStreamReader(inputStream));
            String line;
            while ((line = bri.readLine()) != null) {
                System.out.println(line);
            }
        }).orElseThrow((Function<? super Throwable, RuntimeException>) RuntimeException::new));
    }

}
