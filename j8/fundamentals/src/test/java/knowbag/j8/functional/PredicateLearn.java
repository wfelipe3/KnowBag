package knowbag.j8.functional;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by feliperojas on 2/11/14.
 */
public class PredicateLearn {


    @Test
    public void learn_to_user_predicate_to_filter_collection() {
        List<Integer> ages = Arrays.asList(1, 2, 3, 4, 89, 22, 64);
        Assert.assertArrayEquals(new Integer[]{89, 22, 64}, filterAges(ages, PredicateLearn::isOver21).toArray());
    }

    private static boolean isOver21(Integer age) {
        return age >= 21;
    }

    private static List<Integer> filterAges(List<Integer> ages, Predicate<Integer> p) {
        return ages.stream().filter(p::test).collect(Collectors.toList());
    }
}
