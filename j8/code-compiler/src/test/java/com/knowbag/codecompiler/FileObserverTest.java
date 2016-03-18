package com.knowbag.codecompiler;

import com.knowbag.codecompile.FileWatcherBuilder;
import javaslang.Tuple;
import javaslang.Tuple2;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by feliperojas on 3/16/16.
 */
public class FileObserverTest {

    @Test
    public void whenFileWatcherIsInvokedOnProjectWithoutFoldersThenOnlyWatchProjectFolder() throws InterruptedException, IOException {
        List<Path> folders = new ArrayList<>();
        List<Tuple2<Path, WatchEvent.Kind>> events = new ArrayList<>();
        String projectFolder = "/Users/feliperojas/projects/git/KnowBag/j8/code-compiler/src/test/resources/ProjectWithoutFolders";
        FileWatcherBuilder
                .newFileWatcherForProject(projectFolder)
                .withFolderTraverser(folders::add)
                .create();
        assertThat(folders).containsExactly(Paths.get(projectFolder));
    }

    @Test
    public void whenFileWatcherIsInvokedOnProjectWithOneFolderThenWatchProjectAndFolder() throws Exception {
        List<Path> folders = new ArrayList<>();
        List<Tuple2<Path, WatchEvent.Kind>> events = new ArrayList<>();
        String projectFolder = "/Users/feliperojas/projects/git/KnowBag/j8/code-compiler/src/test/resources/ProjectWithOneFolder";
        FileWatcherBuilder
                .newFileWatcherForProject(projectFolder)
                .withFolderTraverser(folders::add)
                .create();
        assertThat(folders).containsExactly(Paths.get(projectFolder), Paths.get(projectFolder + "/folder1"));
    }

    @Test
    public void whenFileWathcerIsInvokedOnProjectWitnOneFolderAndFilesTheWatchProjectAndFolder() throws Exception {
        List<Path> folders = new ArrayList<>();
        List<Tuple2<Path, WatchEvent.Kind>> events = new ArrayList<>();
        String projectFolder = "/Users/feliperojas/projects/git/KnowBag/j8/code-compiler/src/test/resources/ProjectWithOneFolderAndFiles";
        FileWatcherBuilder
                .newFileWatcherForProject(projectFolder)
                .withFolderTraverser(folders::add)
                .create();
        assertThat(folders).containsExactly(Paths.get(projectFolder), Paths.get(projectFolder + "/folder1"));
    }

    @Test
    public void whenFileWatcherIsInvokedOnProjectWithTwoFoldersAndFilesThenWatchProjectsAndFolders() throws Exception {
        List<Path> folders = new ArrayList<>();
        List<Tuple2<Path, WatchEvent.Kind>> events = new ArrayList<>();
        String projectFolder = "/Users/feliperojas/projects/git/KnowBag/j8/code-compiler/src/test/resources/ProjectWithTwoFolders";
        FileWatcherBuilder
                .newFileWatcherForProject(projectFolder)
                .withFolderTraverser(folders::add)
                .create();
        assertThat(folders).containsExactly(Paths.get(projectFolder), Paths.get(projectFolder + "/folder1"), Paths.get(projectFolder + "/folder2"));
    }

    @Test
    public void whenFileWatcherIsInvokedOnProjectsWithTwoFolderLevelsThenWatchFoldersInAllLevels() throws Exception {
        List<Path> folders = new ArrayList<>();
        List<Tuple2<Path, WatchEvent.Kind>> events = new ArrayList<>();
        String projectFolder = "/Users/feliperojas/projects/git/KnowBag/j8/code-compiler/src/test/resources/ProjectWithMultipleLevels";
        FileWatcherBuilder
                .newFileWatcherForProject(projectFolder)
                .withFolderTraverser(folders::add)
                .create();
        assertThat(folders).containsExactly(Paths.get(projectFolder), Paths.get(projectFolder + "/folder1"), Paths.get(projectFolder + "/folder1/folder11"));
    }

}
