package com.moon.scripting.markdown.generator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The different class types which require generator implementations
 *
 * @author Lennox
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
public enum GeneratorType {
    DATA("Lcom/moon/scripting/documentation/annotation/DataClassContainer;"),
    FUNCTION("Lcom/moon/scripting/documentation/annotation/FunctionContainer;"),
    ENUM("Lcom/moon/scripting/documentation/annotation/EnumContainer;"),
    TABLE_INFO("Lcom/moon/scripting/documentation/annotation/TableInfoContainer;");
    private final String desc;

    /**
     * Finds a generator by an annotation desc
     *
     * @param desc The annotation desc
     * @return The generator type or null
     * @since 1.0.0
     */
    public static GeneratorType findGeneratorOrNull(String desc) {
        for (GeneratorType value : values()) {
            // Return type if desc equals
            if (value.desc.equals(desc)) {
                return value;
            }
        }
        return null;
    }
}
