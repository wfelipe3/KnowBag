package com.knowbag.codecompiler;

import com.knowbag.codecompile.FileWatcherBuilder;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by feliperojas on 3/16/16.
 */
public class FileObserverTest {

    @Test
    public void whenFileWatcherIsInvokedOnProjectWithoutFoldersThenOnlyWatchProjectFolder() throws InterruptedException, IOException {
        Path projectWithoutFolders = createProjectDirectory("ProjectWithoutFolders");
        List<Path> folders = traverseFromProjectFolder(projectWithoutFolders.toString());
        assertThat(folders).containsExactly(projectWithoutFolders);
    }

    @Test
    public void whenFileWatcherIsInvokedOnProjectWithOneFolderThenWatchProjectAndFolder() throws Exception {
        Path projectWithOneFolder = createProjectDirectory("ProjectWithOneFolder");
        Path folder1 = createSubDirectory(projectWithOneFolder, "folder1");
        List<Path> folders = traverseFromProjectFolder(projectWithOneFolder.toString());
        assertThat(folders).containsExactly(projectWithOneFolder, folder1);
    }

    @Test
    public void whenFileWathcerIsInvokedOnProjectWitnOneFolderAndFilesTheWatchProjectAndFolder() throws Exception {
        Path project = createProjectDirectory("ProjectWithOneFolderAndFiles");
        Path folder1 = createSubDirectory(project, "folder1");
        createFile(project, "file.txt");
        List<Path> folders = traverseFromProjectFolder(project.toString());
        assertThat(folders).containsExactly(project, folder1);
    }

    @Test
    public void whenFileWatcherIsInvokedOnProjectWithTwoFoldersAndFilesThenWatchProjectsAndFolders() throws Exception {
        Path project = createProjectDirectory("ProjectWithTwoFolders");
        Path folder1 = createSubDirectory(project, "folder1");
        Path folder2 = createSubDirectory(project, "folder2");
        createFile(project, "file.txt");
        List<Path> folders = traverseFromProjectFolder(project.toString());
        assertThat(folders).containsExactly(project, folder1, folder2);
    }

    @Test
    public void whenFileWatcherIsInvokedOnProjectsWithTwoFolderLevelsThenWatchFoldersInAllLevels() throws Exception {
        Path project = createProjectDirectory("ProjectWithMultipleLevels");
        Path folder1 = createSubDirectory(project, "folder1");
        Path folder11 = createSubDirectory(folder1, "folder11");
        List<Path> folders = traverseFromProjectFolder(project.toString());
        assertThat(folders).containsExactly(project, folder1, folder11);
    }

    private List<Path> traverseFromProjectFolder(String projectFolder) {
        List<Path> folders = new ArrayList<>();
        FileWatcherBuilder
                .newFileWatcherForProject(projectFolder)
                .withFolderTraverser(folders::add)
                .create();
        return folders;
    }

    private Path createProjectDirectory(String project) throws IOException {
        return Files.createTempDirectory(project);
    }

    private Path createSubDirectory(Path projectWithOneFolder, String directory) throws IOException {
        return Files.createDirectory(Paths.get(projectWithOneFolder.toString(), directory));
    }

    private void createFile(Path project, String file) throws IOException {
        Files.write(Paths.get(project.toString(), file), "this is a test".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
