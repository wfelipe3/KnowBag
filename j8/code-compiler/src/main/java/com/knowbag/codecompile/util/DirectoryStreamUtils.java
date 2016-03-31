package com.knowbag.codecompile.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by WilliamE on 31/03/2016.
 */
public class DirectoryStreamUtils {

    public static Stream<Path> toStream(DirectoryStream<Path> ds) {
        return StreamSupport.stream(ds.spliterator(), true);
    }

    public static <T> T traverse(Path root, Function<Stream<Path>, T> f) {
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(root)) {
            return f.apply(toStream(ds));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void traverse(Path root, Consumer<Stream<Path>> consumer) {
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(root)) {
            consumer.accept(toStream(ds));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
