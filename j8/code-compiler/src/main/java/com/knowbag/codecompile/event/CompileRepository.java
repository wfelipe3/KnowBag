package com.knowbag.codecompile.event;

import java.nio.file.Path;

/**
 * Created by WilliamE on 31/03/2016.
 */
public interface CompileRepository {
    void add(Path projectPath);
}
