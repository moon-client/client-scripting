package com.moon.scripting.markdown.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

/**
 * Loads {@link ClassNode}s from a given zip file
 *
 * @author Lennox
 * @since 1.0.0
 */
public class ClassFileLoader {
    private static final TaggedLogger LOGGER = Logger.tag("classfileloader");

    /**
     * Loads all class nodes from the given zip file
     *
     * @param zip The zip file
     * @return The class nodes
     * @since 1.0.0
     */
    public static List<ClassNode> loadClassNodesFrom(ZipFile zip) {
        List<ClassNode> nodes = new ArrayList<>();
        zip.stream().forEach(zipEntry -> {
            // Ignore directories
            if (!zipEntry.isDirectory()) {
                // Only accept class files
                String fileName = zipEntry.getName();
                if (fileName.endsWith(".class")) {
                    try {
                        InputStream stream = zip.getInputStream(zipEntry);
                        byte[] bytes = stream.readAllBytes();

                        ClassReader reader = new ClassReader(bytes);
                        ClassNode node = new ClassNode();
                        reader.accept(node, ClassReader.EXPAND_FRAMES);
                        nodes.add(node);
                    } catch (IOException e) {
                        LOGGER.error("Failed to load class " + fileName, e);
                    }
                }
            }
        });
        return nodes;
    }
}
