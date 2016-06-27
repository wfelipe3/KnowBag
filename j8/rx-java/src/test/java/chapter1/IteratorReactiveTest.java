package chapter1;

import org.junit.Test;
import rx.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by WilliamE on 27/06/2016.
 */
public class IteratorReactiveTest {

    @Test
    public void iterateListWithObservable() throws Exception {
        List<String> otherValue = new ArrayList<>();
        Observable.from(Arrays.asList("One", "two", "Three", "Four", "Five"))
                .subscribe(otherValue::add, e -> otherValue.add("error"), () -> otherValue.add("finished"));
        assertThat(otherValue).containsExactly("One", "two", "Three", "Four", "Five", "finished");
    }

    @Test
    public void iterateListWithExceptionInObservable() throws Exception {
        List<String> otherValue = new ArrayList<>();
        Observable.from(Arrays.asList("One", "two", "Three", "Four", "Five"))
                .subscribe(Integer::parseInt, e -> otherValue.add("error"), () -> otherValue.add("finished"));
        assertThat(otherValue).containsExactly("error");
    }
}
