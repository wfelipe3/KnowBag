package knowbag.j8.functional;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by feliperojas on 2/11/14.
 */
public class FunctionLearn {

    @Test
    public void learn_to_pass_function_to_method() {
        List<Integer> ages = getFixedAges();
        ages.sort(FunctionLearn::sortAscendant);

        Assert.assertArrayEquals(new Integer[]{1, 5, 23, 67}, ages.toArray());
    }

    @Test
    public void learn_to_pass_anonymous_function_to_method() {
        List<Integer> ages = getFixedAges();
        ages.sort((age1, age2) -> age1.compareTo(age2));
        Assert.assertArrayEquals(new Integer[]{1, 5, 23, 67}, ages.toArray());
    }

    public static Integer sortAscendant(Integer age1, Integer age2) {
        return age1.compareTo(age2);
    }

    private List<Integer> getFixedAges() {
        return Arrays.asList(23, 5, 67, 1);
    }
}
