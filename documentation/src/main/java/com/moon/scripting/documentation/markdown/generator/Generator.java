package com.moon.scripting.documentation.markdown.generator;

import com.moon.scripting.documentation.markdown.generator.type.visitor.AnnotationValueVisitor;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import java.util.List;

/**
 * The base generator class used to generate markdown files from class files
 *
 * @author Lennox
 * @since 1.0.0
 */
public abstract class Generator {
    /**
     * Generates a markdown file from a given class node
     *
     * @param node The class node
     * @return The generated markdown
     * @since 1.0.0
     */
    public abstract GeneratorResult generate(ClassNode node);

    /**
     * Tries to find an annotation in a given class node
     *
     * @param node The class node
     * @param desc The annotation desc
     * @return The annotation node or null
     * @since 1.0.0
     */
    protected AnnotationNode findAnnotation(ClassNode node, String desc) {
        // Abort if no annotations are present
        if (node.visibleAnnotations == null) {
            return null;
        }
        for (AnnotationNode annotation : node.visibleAnnotations) {
            // Return annotation if desc is matching
            if (annotation.desc.equals(desc)) {
                return annotation;
            }
        }
        return null;
    }

    /**
     * Tries to find an annotation in a given method node
     *
     * @param node The method node
     * @param desc The annotation desc
     * @return The annotation node or null
     * @since 1.0.0
     */
    protected AnnotationNode findAnnotation(MethodNode node, String desc) {
        // Abort if no annotations are present
        if (node.visibleAnnotations == null) {
            return null;
        }
        for (AnnotationNode annotation : node.visibleAnnotations) {
            // Return annotation if desc is matching
            if (annotation.desc.equals(desc)) {
                return annotation;
            }
        }
        return null;
    }

    /**
     * Tries to find an annotation in a given field node
     *
     * @param node The field node
     * @param desc The annotation desc
     * @return The annotation node or null
     * @since 1.0.0
     */
    protected AnnotationNode findAnnotation(FieldNode node, String desc) {
        // Abort if no annotations are present
        if (node.visibleAnnotations == null) {
            return null;
        }
        for (AnnotationNode annotation : node.visibleAnnotations) {
            // Return annotation if desc is matching
            if (annotation.desc.equals(desc)) {
                return annotation;
            }
        }
        return null;
    }

    /**
     * Tries to find an annotation in a given node list
     *
     * @param nodes The annotation nodes
     * @param desc  The annotation desc
     * @return The annotation node or null
     * @since 1.0.0
     */
    protected AnnotationNode findAnnotation(List<AnnotationNode> nodes, String desc) {
        for (AnnotationNode annotation : nodes) {
            // Return annotation if desc is matching
            if (annotation.desc.equals(desc)) {
                return annotation;
            }
        }
        return null;
    }

    protected Object findAnnotationValue(AnnotationNode node, String key) {
        AnnotationValueVisitor visitor = new AnnotationValueVisitor();
        node.accept(visitor);
        return visitor.get(key);
    }
}
