package com.moon.scripting.documentation.markdown.generator.type;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.FunctionContainer;
import com.moon.scripting.documentation.annotation.Parameter;
import com.moon.scripting.documentation.markdown.generator.Generator;
import com.moon.scripting.documentation.markdown.generator.GeneratorResult;
import com.moon.scripting.documentation.markdown.generator.type.visitor.AnnotationValueVisitor;
import com.moon.scripting.documentation.markdown.processor.AnnotationMappings;
import net.steppschuh.markdowngenerator.table.Table;
import net.steppschuh.markdowngenerator.text.Text;
import net.steppschuh.markdowngenerator.text.heading.Heading;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;
import java.util.List;

/**
 * The generator for {@link FunctionContainer}s
 *
 * @author Lennox
 * @since 1.0.0
 */
public class FunctionGenerator extends Generator {
    @Override
    public GeneratorResult generate(ClassNode node) {
        String path = "";
        StringBuilder generated = new StringBuilder();
        AnnotationNode funcNode = findAnnotation(node, AnnotationMappings.getMappingsFor(FunctionContainer.class));
        AnnotationNode descNode = findAnnotation(node, AnnotationMappings.getMappingsFor(Description.class));
        // Append class name if present
        if (funcNode != null) {
            AnnotationValueVisitor visitor = new AnnotationValueVisitor();
            funcNode.accept(visitor);
            String name = (String) visitor.get("value");
            path = (String) visitor.get("path");
            generated.append(new Heading(name, 1)).append("\n");
        }
        // Append class description if present
        if (descNode != null) {
            AnnotationValueVisitor visitor = new AnnotationValueVisitor();
            descNode.accept(visitor);
            String description = (String) visitor.get("value");
            generated.append(new Text(description)).append("\n").append("\n");
        }
        for (MethodNode method : node.methods) {
            generated.append(new Heading(method.name, 2)).append("\n");
            AnnotationNode methodDescNode = findAnnotation(method,
                AnnotationMappings.getMappingsFor(Description.class));
            if (methodDescNode != null) {
                AnnotationValueVisitor visitor = new AnnotationValueVisitor();
                methodDescNode.accept(visitor);
                String description = (String) visitor.get("value");
                generated.append(new Text(description)).append("\n").append("\n");
            }

            Type methodType = Type.getMethodType(method.desc);
            Type[] parameters = methodType.getArgumentTypes();
            // Handle parameters if there are any
            if (parameters.length > 0) {
                List<AnnotationNode>[] parameterAnnotations = method.visibleParameterAnnotations;
                Table.Builder tableBuilder = new Table.Builder().addRow("type", "name", "description");
                for (int i = 0; i < parameters.length; i++) {
                    Type parameter = parameters[i];
                    List<AnnotationNode> annotations = parameterAnnotations[i];
                    AnnotationNode paramNode = findAnnotation(annotations,
                        AnnotationMappings.getMappingsFor(Parameter.class));
                    AnnotationNode paramDescNode = findAnnotation(annotations,
                        AnnotationMappings.getMappingsFor(Description.class));
                    String description = "No description";
                    String name = "ERROR";
                    if (paramNode != null) {
                        AnnotationValueVisitor visitor = new AnnotationValueVisitor();
                        paramNode.accept(visitor);
                        name = (String) visitor.get("value");
                    }
                    if (paramDescNode != null) {
                        AnnotationValueVisitor visitor = new AnnotationValueVisitor();
                        paramDescNode.accept(visitor);
                        description = (String) visitor.get("value");
                    }
                    String fullClassName = parameter.getClassName();
                    String[] namePats = fullClassName.split("\\.");
                    String className = namePats[namePats.length - 1].toLowerCase();
                    tableBuilder.addRow(name, className, description);
                    System.out.println(className);
                }
                generated.append(tableBuilder.build().serialize()).append("\n");
            }
        }
        return GeneratorResult.builder()
            .markdown(generated.toString())
            .path(path)
            .build();
    }
}
