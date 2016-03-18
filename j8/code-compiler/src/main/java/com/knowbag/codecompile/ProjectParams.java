package com.knowbag.codecompile;

import java.nio.file.Path;

/**
 * Created by WilliamE on 18/03/2016.
 */
public class ProjectParams {
    private final String projectFile;
    private final Path compile;

    public ProjectParams(String projectFile, Path compile) {
        this.projectFile = projectFile;
        this.compile = compile;
    }

    public String getProjectFile() {
        return projectFile;
    }

    public Path getCompile() {
        return compile;
    }
}
