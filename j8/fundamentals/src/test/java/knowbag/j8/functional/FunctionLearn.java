package knowbag.j8.functional;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public void learn_to_behavior_parameterization() {
        List<Apple> apples = Arrays.asList(new Apple("green", 12), new Apple("red", 34));
        Assert.assertEquals("green red", printApples(apples, new ColorAppleFormatter()));
        Assert.assertEquals("green red", printApples(apples, (apple) -> apple.color));
    }

    @Test
    public void learn_to_create_lambdas_with_functional_interfaces() {
        List<Apple> apples = Arrays.asList(new Apple("green", 12), new Apple("red", 34));
        Comparator<Apple> comparator = (Apple a1, Apple a2) -> a2.color.compareToIgnoreCase(a1.color);
        sort(() -> apples.sort(comparator));
        List<String> appleColors = apples.stream().map(a -> a.color).collect(Collectors.toList());
        Assert.assertArrayEquals(new String[]{"red", "green"}, appleColors.toArray());
    }

    private void sort(Runnable r) {
        r.run();
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
