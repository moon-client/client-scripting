package com.moon.scripting.documentation.markdown.util;

import org.tinylog.Logger;
import org.tinylog.TaggedLogger;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileUtils {
    private static final TaggedLogger LOGGER = Logger.tag("markdown/files");

    /**
     * Saves a file with given content
     *
     * @param file    The file
     * @param content The content
     * @since 1.0.0
     */
    public static void saveFile(File file, String content) {
        // Create parental folders
        if (file.getParentFile() != null) {
            //noinspection ResultOfMethodCallIgnored
            file.getParentFile().mkdirs();
        }
        // Delete already existing doc files
        if (file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
        try {
            Files.write(file.toPath(), content.getBytes(), StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            LOGGER.error("Failed to save file " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }
}
