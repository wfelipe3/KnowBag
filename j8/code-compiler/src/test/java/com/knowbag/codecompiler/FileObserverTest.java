package com.knowbag.codecompiler;

import com.knowbag.codecompile.FileWatcherBuilder;
import javaslang.Tuple2;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by feliperojas on 3/16/16.
 */
public class FileObserverTest {

    public static final String RESOURCES;

    static {
        String os = System.getProperty("os.name");
        System.out.println(os);
        switch (os) {
            case "osx":
                RESOURCES = "/Users/feliperojas/projects/git/KnowBag/j8/code-compiler/src/test/resources";
                break;
            case "Windows 8.1":
                RESOURCES = "C:\\dev\\git\\KnowBag\\j8\\code-compiler\\src\\test\\resources";
                break;
            default:
                RESOURCES = "undefined";
        }
    }

    @Test
    public void whenFileWatcherIsInvokedOnProjectWithoutFoldersThenOnlyWatchProjectFolder() throws InterruptedException, IOException {
        List<Path> folders = new ArrayList<>();
        String projectFolder = RESOURCES + "/ProjectWithoutFolders";
        FileWatcherBuilder
                .newFileWatcherForProject(projectFolder)
                .withFolderTraverser(folders::add)
                .create();
        assertThat(folders).containsExactly(Paths.get(projectFolder));
    }

    @Test
    public void whenFileWatcherIsInvokedOnProjectWithOneFolderThenWatchProjectAndFolder() throws Exception {
        List<Path> folders = new ArrayList<>();
        String projectFolder = RESOURCES + "/ProjectWithOneFolder";
        FileWatcherBuilder
                .newFileWatcherForProject(projectFolder)
                .withFolderTraverser(folders::add)
                .create();
        assertThat(folders).containsExactly(Paths.get(projectFolder), Paths.get(projectFolder + "/folder1"));
    }

    @Test
    public void whenFileWathcerIsInvokedOnProjectWitnOneFolderAndFilesTheWatchProjectAndFolder() throws Exception {
        List<Path> folders = new ArrayList<>();
        String projectFolder = RESOURCES + "/ProjectWithOneFolderAndFiles";
        FileWatcherBuilder
                .newFileWatcherForProject(projectFolder)
                .withFolderTraverser(folders::add)
                .create();
        assertThat(folders).containsExactly(Paths.get(projectFolder), Paths.get(projectFolder + "/folder1"));
    }

    @Test
    public void whenFileWatcherIsInvokedOnProjectWithTwoFoldersAndFilesThenWatchProjectsAndFolders() throws Exception {
        List<Path> folders = new ArrayList<>();
        String projectFolder = RESOURCES + "/ProjectWithTwoFolders";
        FileWatcherBuilder
                .newFileWatcherForProject(projectFolder)
                .withFolderTraverser(folders::add)
                .create();
        assertThat(folders).containsExactly(Paths.get(projectFolder), Paths.get(projectFolder + "/folder1"), Paths.get(projectFolder + "/folder2"));
    }

    @Test
    public void whenFileWatcherIsInvokedOnProjectsWithTwoFolderLevelsThenWatchFoldersInAllLevels() throws Exception {
        List<Path> folders = new ArrayList<>();
        String projectFolder = RESOURCES + "/ProjectWithMultipleLevels";
        FileWatcherBuilder
                .newFileWatcherForProject(projectFolder)
                .withFolderTraverser(folders::add)
                .create();
        assertThat(folders).containsExactly(Paths.get(projectFolder), Paths.get(projectFolder + "/folder1"), Paths.get(projectFolder + "/folder1/folder11"));
    }

}
