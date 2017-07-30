package com.poc.vocreater;

import javaslang.Tuple;
import javaslang.Tuple2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static javaslang.API.Case;
import static javaslang.API.Match;

/**
 * Created by dev-williame on 4/20/17.
 */
public class App {

    public static void main(String args[]) throws IllegalAccessException, InstantiationException {
        UserVO user = createVO(UserVO.class, App::getGenerator);
        System.out.println(user);
    }

    private static <T> T createVO(Class<T> voClass, Function<Class<?>, Supplier<Object>> generator) {
        try {
            return (T) Stream.of(voClass.getDeclaredMethods())
                    .filter(m -> m.getName().startsWith("set"))
                    .map(m -> Tuple.of(m, m.getParameterTypes()[0]))
                    .map(t -> Tuple.of(t._1, generator.apply(t._2)))
                    .reduce(voClass.newInstance(), insertPropertiesToVO(), (vo1, vo2) -> vo2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static BiFunction<Object, Tuple2<Method, Supplier<Object>>, Object> insertPropertiesToVO() {
        return (u, t) -> {
            try {
                t._1.invoke(u, t._2.get());
                return u;
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private static Supplier<Object> getGenerator(Class<?> type) {
        return Match(type).of(
                Case(t -> t == int.class, t -> () -> new Random().nextInt()),
                Case(t -> t == String.class, t -> () -> "this is a test"),
                Case(t -> t == Address.class, t -> () -> createVO(Address.class, App::getGenerator))
        );
    }

    public static class Address {
        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "address='" + address + '\'' +
                    '}';
        }
    }

    public static class UserVO {
        private String name;
        private int age;
        private Address address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "UserVO{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", address=" + address +
                    '}';
        }
    }
}
