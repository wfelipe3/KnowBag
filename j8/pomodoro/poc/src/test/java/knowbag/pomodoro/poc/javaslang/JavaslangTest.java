package knowbag.pomodoro.poc.javaslang;

import javaslang.Function1;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.control.Try;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by feliperojas on 1/2/16.
 */
public class JavaslangTest {

    @Test
    public void testTuples() {
        final Tuple2<String, Integer> java8 = Tuple.of("java", 8);
        final Tuple2 guessWhat = java8.map(s -> s + "slang", i -> i / 4);
        assertThat(java8._1(), is("java"));
        assertThat(java8._2(), is(8));
        assertThat(guessWhat._1(), is("javaslang"));
        assertThat(guessWhat._2(), is(2));
    }

    @Test
    public void testTryFunction() {
        Try.CheckedSupplier<List<String>> performIO = () -> Files.readAllLines(Paths.get(""));
        List<String> actual = Try.of(performIO).orElse(Collections.emptyList());
        assertThat(actual, is(Collections.emptyList()));
    }

    @Test
    public void testMemoizedFunction() {
        List<String> values = new ArrayList<>();
        final Function1<String, String> f = Function1.<String, String>of(s -> {
            values.add(s);
            return s;
        }).memoized();

        f.apply("test");
        f.apply("test1");

        assertThat(values, hasItems("test"));
    }
}
