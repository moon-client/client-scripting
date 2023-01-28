package com.moon.scripting.markdown.processor;

import com.moon.scripting.documentation.markdown.generator.Generator;
import com.moon.scripting.documentation.markdown.generator.GeneratorFactory;
import com.moon.scripting.documentation.markdown.generator.GeneratorResult;
import com.moon.scripting.documentation.markdown.generator.GeneratorType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

/**
 * Processes given classes by determining their {@link GeneratorType} which is then used to create a documentation file
 * in Markdown formatting
 * <p>
 * The class processor automatically saves the output Markdown file
 *
 * @author Lennox
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
public class ClassProcessor {
    private static final TaggedLogger LOGGER = Logger.tag("markdown/processor");
    private static final File OUTPUT_PATH = new File("generated");
    private final ClassNode node;

    /**
     * Processes the input class node
     *
     * @since 1.0.0
     */
    public GeneratorResult process() {
        GeneratorType type = resolveGeneratorType();
        // Only process classes with a generator type
        if (type != null) {
            LOGGER.info("Creating documentation for class " + node.name);
            Generator generator = GeneratorFactory.createGenerator(type);
            GeneratorResult result = generator.generate(node);

            File outputFile = new File(OUTPUT_PATH, result.getPath() + ".md");
            // Create parental folders
            if (outputFile.getParentFile() != null) {
                //noinspection ResultOfMethodCallIgnored
                outputFile.getParentFile().mkdirs();
            }
            // Delete already existing doc files
            if (outputFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                outputFile.delete();
            }
            try {
                Files.write(outputFile.toPath(), result.getMarkdown().getBytes(), StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                LOGGER.error("Failed to save documentation for class " + node.name);
                e.printStackTrace();
            }
            LOGGER.info("Saved documentation for class " + node.name);
            return result;
        }
        return null;
    }

    /**
     * Resolves the generator type of the input class node
     *
     * @return The generator type
     * @since 1.0.0
     */
    private GeneratorType resolveGeneratorType() {
        for (AnnotationNode visibleAnnotation : node.visibleAnnotations) {
            GeneratorType type = GeneratorType.findGeneratorOrNull(visibleAnnotation.desc);
            // If valid type was found return it
            if (type != null) {
                return type;
            }
        }
        return null;
    }

    static {
        // Create output path if it doesn't exist
        if (!OUTPUT_PATH.exists()) {
            OUTPUT_PATH.mkdirs();
        }
    }
}
