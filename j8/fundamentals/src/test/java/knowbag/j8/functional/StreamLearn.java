package knowbag.j8.functional;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Created by feliperojas on 26/01/15.
 */
public class StreamLearn {

    @Test
    public void testBasicStream() {
        List<Dish> dishes = getDishes();

        Map<String, List<Dish>> typeDishes = dishes.stream()
                .filter(d -> d.getCalories() < 500)
                .collect(groupingBy(Dish::getType));

        typeDishes.forEach((key, value) -> {
            StringBuilder b = new StringBuilder();
            b.append("key: ");
            b.append(key);
            b.append(" value:{ ");
            b.append(value.stream().map(Dish::toString).collect(joining(", ")));
            System.out.println(b.toString());
        });
    }

    @Test(expected = IllegalStateException.class)
    public void testTraveseJustOnce() {
        List<Dish> dishes = getDishes();
        Stream<Dish> stream = dishes.stream();
        stream.forEach(System.out::println);
        stream.forEach(System.out::println);
    }

    @Test
    public void testSquareList() {
        List<Integer> values = Arrays.asList(1,2,3,4,5,6,7);
        List<Double> sqrtValues = values.stream().map(Math::sqrt).collect(toList());
        System.out.println(sqrtValues);
    }

    private List<Dish> getDishes() {
        return Arrays.asList(new Dish(100, "sea"), new Dish(250, "salad"),
                new Dish(800, "colombian"), new Dish(300, "sea"));
    }

    class Dish {
        private int calories;
        private String type;

        Dish(int calories, String type) {
            this.calories = calories;
            this.type = type;
        }

        public int getCalories() {
            return calories;
        }

        public void setCalories(int calories) {
            this.calories = calories;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Dish{" +
                    "calories=" + calories +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
