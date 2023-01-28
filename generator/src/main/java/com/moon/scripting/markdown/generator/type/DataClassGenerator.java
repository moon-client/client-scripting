package com.moon.scripting.markdown.generator.type;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.Parameter;
import com.moon.scripting.markdown.generator.Generator;
import com.moon.scripting.markdown.generator.GeneratorResult;
import com.moon.scripting.markdown.processor.AnnotationMappings;
import java.util.List;
import net.steppschuh.markdowngenerator.table.Table;
import net.steppschuh.markdowngenerator.text.Text;
import net.steppschuh.markdowngenerator.text.emphasis.BoldText;
import net.steppschuh.markdowngenerator.text.heading.Heading;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public class DataClassGenerator extends Generator {
    @Override
    public GeneratorResult generate(ClassNode node) {
        String path = "";
        StringBuilder generated = new StringBuilder();
        AnnotationNode dataNode = findAnnotation(node, AnnotationMappings.mappingsFor(DataClassContainer.class));
        AnnotationNode descNode = findAnnotation(node, AnnotationMappings.mappingsFor(Description.class));
        // Append class name if present
        String name = "No name found";
        if (dataNode != null) {
            name = (String) findAnnotationValue(dataNode, "value");
            path = (String) findAnnotationValue(dataNode, "path");
            generated.append(new Heading(name, 1)).append("\n");
        }
        // Append class description if present
        if (descNode != null) {
            String description = (String) findAnnotationValue(descNode, "value");
            generated.append(new Text(description)).append("\n").append("\n");
        }

        // Handle fields if present
        if (node.fields.size() > 0) {
            generated.append(new Heading("Fields", 2)).append("\n");
            generated.append(new Text("All fields contained in this type")).append("\n").append("\n");
            Table.Builder values = new Table.Builder().addRow("field", "description");
            for (FieldNode field : node.fields) {
                AnnotationNode paramDescNode = findAnnotation(field,
                    AnnotationMappings.mappingsFor(Description.class));
                String description = "No description";
                // Load enum value description
                if (paramDescNode != null) {
                    description = (String) findAnnotationValue(paramDescNode, "value");
                }
                values.addRow(field.name, description);
            }
            generated.append(values.build().serialize()).append("\n").append("\n");
        }

        // Handle methods if present
        if (node.methods.size() > 0) {
            generated.append(new Heading("Methods", 2)).append("\n");
            generated.append(new Text("All methods contained in this type")).append("\n").append("\n");
            for (MethodNode method : node.methods) {
                // Ignore <init> and <clinit>
                if (method.name.startsWith("<")) {
                    continue;
                }

                generated.append(new Heading(method.name + "()", 3)).append("\n");
                AnnotationNode methodDescNode = findAnnotation(method,
                    AnnotationMappings.mappingsFor(Description.class));
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
                                AnnotationMappings.mappingsFor(Parameter.class));
                            AnnotationNode paramDescNode = findAnnotation(annotations,
                                AnnotationMappings.mappingsFor(Description.class));
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
                        generated.append(tableBuilder.build().serialize()).append("\n");
                    } else {
                        generated.append(new BoldText("No parameters found")).append("\n").append("\n");
                    }
                } else {
                    generated.append(new BoldText("This method does not have any parameters"))
                        .append("\n")
                        .append("\n");
                }
            }
        }
        return GeneratorResult.builder()
            .name(name)
            .path(path)
            .markdown(generated.toString())
            .build();
    }
}
