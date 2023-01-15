package com.moon.scripting.documentation.markdown.generator.type;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.FunctionContainer;
import com.moon.scripting.documentation.annotation.Parameter;
import com.moon.scripting.documentation.annotation.TableInfoContainer;
import com.moon.scripting.documentation.markdown.generator.Generator;
import com.moon.scripting.documentation.markdown.generator.GeneratorResult;
import com.moon.scripting.documentation.markdown.processor.AnnotationMappings;
import java.util.List;
import net.steppschuh.markdowngenerator.table.Table;
import net.steppschuh.markdowngenerator.text.Text;
import net.steppschuh.markdowngenerator.text.emphasis.BoldText;
import net.steppschuh.markdowngenerator.text.heading.Heading;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * The generator for {@link FunctionContainer}s
 *
 * @author Lennox
 * @since 1.0.0
 */
public class TableInfoGenerator extends Generator {
    @Override
    public GeneratorResult generate(ClassNode node) {
        String path = "";
        StringBuilder generated = new StringBuilder();
        AnnotationNode tableNode = findAnnotation(node, AnnotationMappings.getMappingsFor(TableInfoContainer.class));
        AnnotationNode descNode = findAnnotation(node, AnnotationMappings.getMappingsFor(Description.class));
        // Append class name if present
        String name = "No name found";
        if (tableNode != null) {
            name = (String) findAnnotationValue(tableNode, "value");
            path = (String) findAnnotationValue(tableNode, "path");
            generated.append(new Heading(name, 1)).append("\n");
        }
        // Append class description if present
        if (descNode != null) {
            String description = (String) findAnnotationValue(descNode, "value");
            generated.append(new Text(description)).append("\n").append("\n");
        }
        for (MethodNode method : node.methods) {
            // Ignore <init> and <clinit>
            if (method.name.startsWith("<")) {
                continue;
            }

            generated.append(new Heading(method.name, 2)).append("\n");
            AnnotationNode methodDescNode = findAnnotation(method,
                AnnotationMappings.getMappingsFor(Description.class));
            // Append method description if present
            if (methodDescNode != null) {
                String description = (String) findAnnotationValue(methodDescNode, "value");
                generated.append(new Text(description)).append("\n").append("\n");
            }

            Type methodType = Type.getMethodType(method.desc);
            Type[] parameters = methodType.getArgumentTypes();
            // Handle parameters if there are any
            if (parameters.length > 0) {
                List<AnnotationNode>[] parameterAnnotations = method.visibleParameterAnnotations;
                if (parameterAnnotations != null) {
                    generated.append(new Heading("Parameters", 4)).append("\n").append("\n");
                    Table.Builder tableBuilder = new Table.Builder().addRow("name", "type", "description");
                    for (int i = 0; i < parameters.length; i++) {
                        Type parameter = parameters[i];
                        List<AnnotationNode> annotations = parameterAnnotations[i];
                        AnnotationNode paramNode = findAnnotation(annotations,
                            AnnotationMappings.getMappingsFor(Parameter.class));
                        AnnotationNode paramDescNode = findAnnotation(annotations,
                            AnnotationMappings.getMappingsFor(Description.class));
                        String description = "No description";
                        String paramName = "ERROR";
                        // Load parameter name
                        if (paramNode != null) {
                            paramName = (String) findAnnotationValue(paramNode, "value");
                        }
                        // Load parameter description
                        if (paramDescNode != null) {
                            description = (String) findAnnotationValue(paramDescNode, "value");
                        }
                        String fullClassName = parameter.getClassName();
                        String[] namePats = fullClassName.split("\\.");
                        String className = namePats[namePats.length - 1].toLowerCase();
                        tableBuilder.addRow(paramName, className, description);
                    }
                    generated.append(tableBuilder.build().serialize()).append("\n").append("\n");
                }
            }
        }
        return GeneratorResult.builder()
            .markdown(generated.toString())
            .path(path)
            .name(name)
            .build();
    }
}
