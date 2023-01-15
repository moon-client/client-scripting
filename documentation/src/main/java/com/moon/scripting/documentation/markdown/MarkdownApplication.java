package com.moon.scripting.documentation.markdown;

import com.moon.scripting.documentation.markdown.file.ClassFileLoader;
import com.moon.scripting.documentation.markdown.processor.ClassProcessor;
import org.objectweb.asm.tree.ClassNode;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipFile;

/**
 * The moon scripting documentation generator
 * <p>
 * Uses a compiled api module to generate documentation for mdBook with asm.
 *
 * @author Lennox
 * @since 1.0.0
 */
public class MarkdownApplication {
    private static final TaggedLogger LOGGER = Logger.tag("docgen");

    /**
     * Runs the documentation generator
     *
     * @since 1.0.0
     */
    public void run() {
        File inputFile = new File("input.jar");
        // Abort if input file does not exit
        if (!inputFile.exists()) {
            LOGGER.error("The input file does not exist, aborting...");
            System.exit(-1);
        }

        List<ClassNode> classesToProcess = ClassFileLoader.loadClassNodesFrom(createZipFrom(inputFile));
        processNodes(classesToProcess);
    }

    /**
     * Processes a given collection of class nodes and creates documentation off of them
     *
     * @param nodes The class nodes
     * @since 1.0.0
     */
    private void processNodes(List<ClassNode> nodes) {
        for (ClassNode node : nodes) {
            ClassProcessor processor = new ClassProcessor(node);
            processor.process();
        }
    }

    /**
     * Creates a zip file from a normal file
     * <p>
     * Exits the program automatically if the input file is not a zip file
     *
     * @param file The input file
     * @return A zip file
     * @since 1.0.0
     */
    private ZipFile createZipFrom(File file) {
        try {
            return new ZipFile(file);
        } catch (IOException e) {
            LOGGER.error("The provided input file is not a zip file!");
            System.exit(-2);
        }
        return null;
    }
}
