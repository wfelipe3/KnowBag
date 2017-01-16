package com.knowbag.categorytheory;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by dev-williame on 1/11/17.
 */
public class ConceptsTest {

    @Test
    public void test() throws Exception {
        OutputGiver<Integer> f = input -> Integer.parseInt(input.value);
        OutputGiver<String> map = f.map(String::valueOf);

        Input input = new Input("10");
        assertThat(f.giveOutput(input), is(10));
        assertThat(map.giveOutput(input), is("10"));

        InputTake<Integer> g = i -> new Output(String.valueOf(i));
        InputTake<String> stringInputTake = g.contraMap(Integer::parseInt);

        assertThat(g.takeInput(10).value, is("10"));
        assertThat(stringInputTake.takeInput("10").value, is("10"));

        Function<Input, Output> composed = i -> g.takeInput(f.giveOutput(input));
        assertThat(composed.apply(input).value, is("10"));
    }

    @Test
    public void testExtension() throws Exception {
        Extension<String> e = (s, r) -> new Results(s + " " + r.value);
        Results test = e.apply("test", new Results("hello world"));

        assertThat(test.value, is("test hello world"));

        Extension<List<String>> ecm = e.contraMap(l -> l.stream().collect(Collectors.joining("")));
        Results apply = ecm.apply(Arrays.asList("h", "e", "l", "l", "o", " ", "w", "o", "r", "l", "d"), new Results("test"));

        assertThat(apply.value, is("hello world test"));

        Extension<String> compose = e.compose((s, r) -> new Results(s+r.value));
        Results apply1 = compose.apply("source", new Results("poop"));

        assertThat(apply1.value, is("source"));

    }
}

interface OutputGiver<A> {
    A giveOutput(Input input);

    default <B> OutputGiver<B> map(Function<A, B> f) {
        return input -> f.apply(this.giveOutput(input));
    }

    default <B> Function<B, A> contraMap(Function<B, Input> f) {
        return b -> this.giveOutput(f.apply(b));
    }
}

interface InputTake<A> {
    Output takeInput(A a);

    default <B> Function<A, B> map(Function<Output, B> f) {
        return a -> f.apply(takeInput(a));
    }

    default <B> InputTake<B> contraMap(Function<B, A> f) {
        return b -> this.takeInput(f.apply(b));
    }
}

class Input {
    String value;

    public Input(String value) {
        this.value = value;
    }
}

class Output {
    String value;

    public Output(String value) {
        this.value = value;
    }
}

interface Extension<S> {
    Results apply(S source, Results resutls);

    Extension IDENTITY = (s, r) -> r;

    default Extension<S> compose(Extension<S> e) {
        return (src, results) -> e.apply(src, Extension.this.apply(src, results));
    }

    @SafeVarargs
    static <S> Extension<S> composeAll(Extension<S>... extensions) {
        return Stream.of(extensions).reduce(IDENTITY, Extension::compose);
    }

    default <T> Extension<T> contraMap(Function<T, S> f) {
        return (t, results) -> Extension.this.apply(f.apply(t), results);
    }
}

class Results {
    String value;

    public Results(String value) {
        this.value = value;
    }
}
