package com.knowbag.codecompile.event;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * Created by WilliamE on 31/03/2016.
 */
public interface EventProcessor {

    void processFileEvent(Path root, WatchEvent<?> event);
}
