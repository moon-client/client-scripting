package com.moon.scripting.documentation.markdown;

import com.moon.scripting.documentation.markdown.file.ClassFileLoader;
import com.moon.scripting.documentation.markdown.generator.GeneratorResult;
import com.moon.scripting.documentation.markdown.processor.ClassProcessor;
import com.moon.scripting.documentation.markdown.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipFile;
import net.steppschuh.markdowngenerator.link.Link;
import net.steppschuh.markdowngenerator.list.ListBuilder;
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
            OUTPUT_PATH.delete();
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
        List<ListBuilder> lists = buildStaticContentList(staticContent, null);
        for (ListBuilder list : lists) {
            categoryList.append(list);
        }
        for (String path : categoryMap.keySet()) {
            List<GeneratorResult> results = categoryMap.get(path);
            ListBuilder linkList = new ListBuilder();
            for (GeneratorResult result : results) {
                linkList.append(new Link(result.getName(), result.getPath() + ".md"));
            }
            categoryList.append(new Link(path, path + ".md"));
            categoryList.append(linkList);
        }
        summaryContent.append(categoryList);

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
     * @param builder The builder
     * @return The static content
     */
    private List<ListBuilder> buildStaticContentList(File file, ListBuilder builder) {
        List<ListBuilder> builderList = new ArrayList<>();
        for (File listFile : Objects.requireNonNull(file.listFiles())) {
            // Ignore readme
            if (listFile.getName().equals("README.md")) {
                continue;
            }
            // Build content for folder
            if (listFile.isDirectory()) {
                ListBuilder fileBuilder = new ListBuilder();
                fileBuilder.append(new Link(listFile.getName(), ""));
                buildStaticContentList(listFile, fileBuilder);
                builderList.add(fileBuilder);
            } else if (builder != null) {
                builder.append(new Link(listFile.getName(), ""));
            } else {
                ListBuilder listBuilder = new ListBuilder();
                listBuilder.append(new Link(listFile.getName(), ""));
                builderList.add(listBuilder);
            }
        }
        return builderList;
    }
}
