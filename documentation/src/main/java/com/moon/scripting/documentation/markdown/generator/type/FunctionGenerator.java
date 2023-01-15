package com.moon.scripting.documentation.markdown.generator.type;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.ExampleCode;
import com.moon.scripting.documentation.annotation.FunctionContainer;
import com.moon.scripting.documentation.annotation.Parameter;
import com.moon.scripting.documentation.markdown.generator.Generator;
import com.moon.scripting.documentation.markdown.generator.GeneratorResult;
import com.moon.scripting.documentation.markdown.processor.AnnotationMappings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.steppschuh.markdowngenerator.table.Table;
import net.steppschuh.markdowngenerator.text.Text;
import net.steppschuh.markdowngenerator.text.code.CodeBlock;
import net.steppschuh.markdowngenerator.text.emphasis.BoldText;
import net.steppschuh.markdowngenerator.text.heading.Heading;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import java.util.ArrayList;
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
        String name = "No name found";
        if (funcNode != null) {
            name = (String) findAnnotationValue(funcNode, "value");
            path = (String) findAnnotationValue(funcNode, "path");
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

            AnnotationNode exampleNode = findAnnotation(method, AnnotationMappings.getMappingsFor(ExampleCode.class));
            generated.append(new Heading(method.name, 2)).append("\n");
            AnnotationNode methodDescNode = findAnnotation(method,
                AnnotationMappings.getMappingsFor(Description.class));
            // Append method description if present
            if (methodDescNode != null) {
                String description = (String) findAnnotationValue(methodDescNode, "value");
                generated.append(new Text(description)).append("\n").append("\n");
            }

            Type methodType = Type.getMethodType(method.desc);
            Type returnType = Type.getReturnType(method.desc);
            String fullReturnType = returnType.getClassName();
            String[] returnPats = fullReturnType.split("\\.");
            String returnTypeName = returnPats[returnPats.length - 1].toLowerCase();
            Type[] parameters = methodType.getArgumentTypes();
            // Handle parameters if there are any
            Table.Builder tableBuilder = new Table.Builder().addRow("name", "type", "description");
            if (parameters.length > 0) {
                List<AnnotationNode>[] parameterAnnotations = method.visibleParameterAnnotations;
                if (parameterAnnotations != null) {
                    List<ParsedParameter> parsedParameters = new ArrayList<>();
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
                        parsedParameters.add(new ParsedParameter(paramName, className));
                    }
                    generated.append(new CodeBlock(buildFancySyntax(method.name, returnTypeName, parsedParameters), "lua"))
                        .append("\n");
                    generated.append(new Heading("Parameters", 4)).append("\n").append("\n");
                    generated.append(tableBuilder.build().serialize()).append("\n").append("\n");
                } else {
                    generated.append(new CodeBlock(buildFancySyntax(method.name, returnTypeName, List.of()), "lua"))
                        .append("\n");
                    generated.append(new Heading("Parameters", 4)).append("\n").append("\n");
                    generated.append(new BoldText("No parameters found")).append("\n").append("\n");
                }
            } else {
                generated.append(new BoldText("This method does not have any parameters")).append("\n").append("\n");
            }
            // Provide example code if available
            if (exampleNode != null) {
                generated.append(new Heading("Example", 4)).append("\n").append("\n");
                generated.append(new CodeBlock(findAnnotationValue(exampleNode, "value"), "lua")).append("\n");
            }
        }
        return GeneratorResult.builder()
            .markdown(generated.toString())
            .path(path)
            .name(name)
            .build();
    }

    /**
     * Builds a fancy syntax string
     *
     * @param methodName The method name
     * @param returnType The fancy return type
     * @param parameters The parameters
     * @return The fancy syntax
     * @since 1.0.0
     */
    private String buildFancySyntax(String methodName, String returnType, List<ParsedParameter> parameters) {
        StringBuilder syntax = new StringBuilder();
        syntax.append(methodName).append("(");
        // Add params
        for (int i = 0; i < parameters.size(); i++) {
            ParsedParameter parameter = parameters.get(i);
            syntax.append(parameter.name).append(": ").append(parameter.fancyType);
            // Append spliterator if necessary
            if (i < parameters.size() - 1) {
                syntax.append(", ");
            }
        }
        syntax.append("): ").append(returnType);
        return syntax.toString();
    }

    @Getter
    @AllArgsConstructor
    private class ParsedParameter {
        private final String name;
        private final String fancyType;
    }
}
