package com.moon.scripting.markdown;

import com.moon.scripting.markdown.file.ClassFileLoader;
import com.moon.scripting.markdown.generator.GeneratorResult;
import com.moon.scripting.markdown.processor.ClassProcessor;
import com.moon.scripting.markdown.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipFile;
import net.steppschuh.markdowngenerator.link.Link;
import net.steppschuh.markdowngenerator.list.ListBuilder;
import net.steppschuh.markdowngenerator.list.UnorderedList;
import net.steppschuh.markdowngenerator.text.heading.Heading;
import org.objectweb.asm.tree.ClassNode;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

/**
 * The moon scripting documentation generator
 * <p>
 * Uses a compiled api module to generate documentation for mdBook with asm.
 *
 * @author Lennox
 * @since 1.0.0
 */
public class MarkdownApplication {
    private static final File OUTPUT_PATH = new File("generated");
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
        // Delete output path if existing
        if (OUTPUT_PATH.exists()) {
            try {
                org.apache.commons.io.FileUtils.deleteDirectory(OUTPUT_PATH);
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
        //noinspection ResultOfMethodCallIgnored
        OUTPUT_PATH.mkdirs();

        StringBuilder summaryContent = new StringBuilder();
        Map<String, List<GeneratorResult>> categoryMap = new HashMap<>();
        summaryContent.append(new Heading("Summary", 1)).append("\n");
        for (ClassNode node : nodes) {
            ClassProcessor processor = new ClassProcessor(node);
            GeneratorResult result = processor.process();
            if (result != null) {
                // Create list of generator results for category if absent
                String path = result.getPath().split("/")[0];
                List<GeneratorResult> results = categoryMap.computeIfAbsent(path, (k) -> new ArrayList<>());
                results.add(result);
            }
        }

        File staticContent = new File("static");
        ListBuilder categoryList = new ListBuilder();
        categoryList.append(new Link("Introduction", "README.md"));
        for (String path : categoryMap.keySet()) {
            List<GeneratorResult> results = categoryMap.get(path);
            Map<String, ListBuilder> parentLists = new HashMap<>();
            Map<String, ListBuilder> ownerLists = new HashMap<>();
            ListBuilder linkList = new ListBuilder();
            for (GeneratorResult result : results) {
                String name = result.getName();
                String[] splitName = name.split("/");
                if (splitName.length > 1) {
                    String directory = splitName[0];
                    ListBuilder listBuilder = parentLists.computeIfAbsent(directory, (key) -> {
                        ListBuilder parentListBuilder = new ListBuilder();
                        parentListBuilder.append(new Link(directory, path + "/" + directory + ".md"));
                        ListBuilder childList = new ListBuilder();
                        parentListBuilder.append(childList);
                        ownerLists.put(directory, parentListBuilder);
                        return childList;
                    });
                    listBuilder.append(new Link(splitName[1], result.getPath() + ".md"));
                } else {
                    linkList.append(new Link(name, result.getPath() + ".md"));
                }
            }
            categoryList.append(new Link(path, path + ".md"));
            for (String s : parentLists.keySet()) {
                ListBuilder listBuilderParent = ownerLists.get(s);
                categoryList.append(listBuilderParent);
            }
            categoryList.append(linkList);
        }
        summaryContent.append(categoryList).append("\n");
        summaryContent.append(buildStaticContentList(staticContent, staticContent.getAbsolutePath())).append("\n");

        File outputFile = new File(OUTPUT_PATH, "SUMMARY.md");
        FileUtils.saveFile(outputFile, summaryContent.toString());

        try {
            FileUtils.copyDirectory(staticContent.toPath(), OUTPUT_PATH.toPath());
        } catch (IOException e) {
            LOGGER.error("Failed to copy static input into output directory");
            e.printStackTrace();
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

    /**
     * Builds the summary content for the static directory
     *
     * @param file The file
     * @return The static content
     */
    private ListBuilder buildStaticContentList(File file, String absolutePath) {
        ListBuilder builder = new ListBuilder();
        for (File listFile : Objects.requireNonNull(file.listFiles())) {
            // Ignore readme
            if (listFile.getName().equals("README.md")) {
                continue;
            }
            // Build content for folder
            String filePath = listFile.getAbsolutePath().substring(absolutePath.length() + 1);
            if (listFile.isDirectory()) {
                ListBuilder fileBuilder = new ListBuilder();
                builder.append(new Link(listFile.getName(), filePath + ".md"));
                fileBuilder.append(buildStaticContentList(listFile, absolutePath));
                builder.append(fileBuilder);
            } else {
                builder.append(new Link(listFile.getName().substring(0, listFile.getName().length() - 3), filePath));
            }
        }
        return builder;
    }
}
