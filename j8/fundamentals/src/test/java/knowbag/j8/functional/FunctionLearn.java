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

    @Test
    public void learn_to_parametrize_behavior() {
        List<Apple> apples = Arrays.asList(new Apple("green", 12), new Apple("red", 34));
        Assert.assertEquals("green red", printApples(apples, new ColorAppleFormatter()));
        Assert.assertEquals("green red", printApples(apples, (apple) -> apple.color));
    }

    public static Integer sortAscendant(Integer age1, Integer age2) {
        return age1.compareTo(age2);
    }

    private List<Integer> getFixedAges() {
        return Arrays.asList(23, 5, 67, 1);
    }

    private String printApples(List<Apple> apples, AppleFormatter formatter) {
        StringBuilder builder = new StringBuilder();
        for (Apple apple : apples) {
            builder.append(formatter.format(apple));
            builder.append(" ");
        }
        return builder.toString().trim();
    }

    private class Apple {
        String color;
        int weight;

        Apple(String color, int weight) {
            this.color = color;
            this.weight = weight;
        }
    }

    private interface AppleFormatter {
        public String format(Apple apple);
    }

    private class ColorAppleFormatter implements AppleFormatter {

        @Override
        public String format(Apple apple) {
            return apple.color;
        }
    }
}
