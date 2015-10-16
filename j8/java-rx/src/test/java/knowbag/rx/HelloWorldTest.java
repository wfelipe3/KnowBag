package knowbag.rx;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.observables.ConnectableObservable;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by feliperojas on 10/5/15.
 */
public class HelloWorldTest {

    @Test
    public void helloWorld() {
        Observable<String> observable = Observable.from(Arrays.asList("hello", "world"));
        observable.subscribe(System.out::println);
        observable.subscribe(System.out::println, System.err::println, () -> System.out.println("finished"));
    }

    public static void main(String[] args) {
        HelloWorldTest test = new HelloWorldTest();
        Observer<Double> observer = new ReactiveSum();

        Scanner scanner = new Scanner(System.in);

        ConnectableObservable<String> input = test.from(scanner);
        Observable<Double> b = test.varStream("b", input);
        Observable<Double> a = test.varStream("a", input);

        Observable.combineLatest(a, b, (x, y) -> x + y).subscribe(observer);
        Observable.combineLatest(a, b, (x, y) -> x - y).subscribe(observer);

        input.connect();
    }

    private ConnectableObservable<String> from(Scanner scanner) {
        return Observable.<String>create(subscriber -> {
            if (!subscriber.isUnsubscribed()) {
                try {
                    while (!subscriber.isUnsubscribed()) {
                        String line = scanner.nextLine();
                        if (line.equals("exit")) {
                            break;
                        }
                        subscriber.onNext(line);
                    }
                    if (!subscriber.isUnsubscribed())
                        subscriber.onCompleted();
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
            }
        }).publish();
    }

    private Observable<Double> varStream(String name, Observable<String> input) {//\^s*a\s*[:|=]\s*(-?\d+\.?\d*)$
        final Pattern pattern = Pattern.compile(String.format("%s\\s*[:|=]\\s*(\\d+)", name));
        return input
                .map(pattern::matcher)
                .filter(Matcher::matches)
                .map(matcher -> Double.parseDouble(matcher.group(1)));
    }

    public static final class ReactiveSum implements Observer<Double> { // (1)
        private double sum;

        public ReactiveSum() {
            this.sum = 0;
        }

        public void onCompleted() {
            System.out.println("Exiting last sum was : " + this.sum); // (4)
        }

        public void onError(Throwable e) {
            System.err.println("Got an error!"); // (3)
            e.printStackTrace();
        }

        public void onNext(Double sum) {
            this.sum = sum;
            System.out.println("update : a + b = " + sum); // (2)
        }
    }
}
