package com.knowbag.codecompile;

import com.knowbag.codecompile.event.FileCompileRepository;
import com.knowbag.codecompile.event.FileEventProcessor;
import org.apache.commons.cli.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;

/**
 * Created by feliperojas on 3/17/16.
 */
public class AutoCompileMain {

    public static void main(String args[]) throws ParseException, InterruptedException {
        CommandLine cmd = parseCommands(args);
        String projectFile = cmd.getOptionValue("p");
        Path compile = Paths.get(cmd.getOptionValue("c"));

        ProjectParams params = new ProjectParams(projectFile);
        FileWatcher.getFileWatcherBuilder(new FileEventProcessor(new FileCompileRepository(compile)), params).create();

        new CountDownLatch(1).await();
    }

    private static CommandLine parseCommands(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption(new Option("p", true, "project folder"));
        options.addOption(new Option("c", true, "compile projects output"));
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }
}
