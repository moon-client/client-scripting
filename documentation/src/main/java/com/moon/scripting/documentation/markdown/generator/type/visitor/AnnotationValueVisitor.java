package com.moon.scripting.documentation.markdown.generator.type.visitor;

import lombok.Getter;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;
import java.util.HashMap;
import java.util.Map;

/**
 * Reads and saves the values of an annotation
 *
 * @author Lennox
 * @since 1.0.0
 */
@Getter
public class AnnotationValueVisitor extends AnnotationVisitor {
    private final Map<String, Object> valueMap = new HashMap<>();

    public AnnotationValueVisitor() {
        super(Opcodes.ASM9);
    }

    @Override
    public void visit(String name, Object value) {
        valueMap.put(name, value);
    }

    /**
     * Returns a value from the annotation
     *
     * @param key The key / field name
     * @return The value or null
     * @since 1.0.0
     */
    public Object get(String key) {
        return valueMap.get(key);
    }
}
