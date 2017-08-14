package hello.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by williame on 7/3/17.
 */
public class MyThreadsFactory {
    public List<String> createAndExecuteThreads(int times) {
        List<String> results = new ArrayList<>();

        Runnable runThisShit = () -> {
            results.add("Hello "+Thread.currentThread().getName());
        };

        runThisShit.run();

        Thread thread = new Thread(runThisShit);

        IntStream.range(0,times).forEach(i -> thread.start());

        results.add("Done!");

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return results;
    }
}
