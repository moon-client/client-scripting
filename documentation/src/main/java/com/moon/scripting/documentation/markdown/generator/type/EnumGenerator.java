package com.moon.scripting.documentation.markdown.generator.type;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.EnumContainer;
import com.moon.scripting.documentation.markdown.generator.Generator;
import com.moon.scripting.documentation.markdown.generator.GeneratorResult;
import com.moon.scripting.documentation.markdown.processor.AnnotationMappings;
import net.steppschuh.markdowngenerator.table.Table;
import net.steppschuh.markdowngenerator.text.Text;
import net.steppschuh.markdowngenerator.text.heading.Heading;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

/**
 * The generator for {@link EnumContainer}s
 *
 * @author Lennox
 * @since 1.0.0
 */
public class EnumGenerator extends Generator {
    @Override
    public GeneratorResult generate(ClassNode node) {
        String path = "";
        StringBuilder generated = new StringBuilder();
        AnnotationNode enumNode = findAnnotation(node, AnnotationMappings.getMappingsFor(EnumContainer.class));
        AnnotationNode descNode = findAnnotation(node, AnnotationMappings.getMappingsFor(Description.class));
        // Append class name if present
        String name = "No name found";
        if (enumNode != null) {
            name = (String) findAnnotationValue(enumNode, "value");
            path = (String) findAnnotationValue(enumNode, "path");
            generated.append(new Heading(name, 1)).append("\n");
        }
        // Append class description if present
        if (descNode != null) {
            String description = (String) findAnnotationValue(descNode, "value");
            generated.append(new Text(description)).append("\n").append("\n");
        }
        Table.Builder values = new Table.Builder().addRow("name", "description");
        for (FieldNode field : node.fields) {
            // Exclude $VALUES
            if (field.name.startsWith("$VALUES")) {
                continue;
            }
            AnnotationNode paramDescNode = findAnnotation(field,
                AnnotationMappings.getMappingsFor(Description.class));
            String description = "No description";
            // Load enum value description
            if (paramDescNode != null) {
                description = (String) findAnnotationValue(paramDescNode, "value");
            }
            values.addRow(field.name, description);
        }
        generated.append(values.build().serialize()).append("\n").append("\n");
        return GeneratorResult.builder()
            .name(name)
            .path(path)
            .markdown(generated.toString())
            .build();
    }
}
