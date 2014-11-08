package knowbag.j8.functional;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by feliperojas on 7/11/14.
 */
public class FunctionalJ8InterfacesLearn {

    @Test
    public void learn_to_use_predicate_functional_interface() {
        assertThat(ListContext
                .of(Arrays.asList(1, 2, 45, 67, 3, 80, 28))
                .filter(age -> age % 2 == 0)
                .get(), hasItems(2, 80, 28));
    }

    @Test
    public void learn_to_use_consumer_functional_interface() {
        List<Integer> doubleAges = new ArrayList<>();
        ListContext
                .of(Arrays.asList(1, 2, 45, 67, 3, 80, 28))
                .forEach(age -> doubleAges.add(age * 2));
        assertThat(doubleAges, hasItems(1*2, 2*2, 45*2, 67*2, 3*2, 80*2, 28*2));
    }

    @Test
    public void learn_to_use_supplier_functional_interface() {
        // I steel dont understand the use of this interface, maybe later I can see
        // its use
    }

    @Test
    public void learn_to_use_functional_interface() {
        assertThat(ListContext
                .of(Arrays.asList(1, 2, 45, 67, 3, 80, 28))
                .map(Object::toString)
                .get(), hasItems("1", "2", "45", "67", "3", "80", "28"));
    }

    @Test
    public void learn_to_use_all_functional_interfaces() {
        assertThat(ListContext
                .of(Arrays.asList(1, 2, 45, 67, 3, 80, 28))
                .filter(age -> age % 2 == 0)
                .map(Object::toString)
                .forEach(age -> System.out.println(age))
                .get(), hasItems("2", "80", "28"));
    }

    static class ListContext<T> {

        private List<T> list;

        private ListContext(List<T> list) {
            this.list = list;
        }

        static <T>ListContext<T> of(List<T> list) {
            return new ListContext<>(list);
        }

        public ListContext<T> filter(Predicate<T> predicate) {
            List<T> filtered = new ArrayList<>();
            list.forEach(t -> {
                if (predicate.test(t)) {
                    filtered.add(t);
                }
            });
            return ListContext.of(filtered);
        }

        public <U>ListContext<U> map(Function<T, U> function) {
            List<U> mapList = new ArrayList<>();
            list.forEach(t -> mapList.add(function.apply(t)));
            return ListContext.of(mapList);
        }

        public ListContext<T> forEach(Consumer<T> consumer) {
            list.forEach(consumer);
            return this;
        }

        public List<T> get() {
            return list;
        }

    }
}
