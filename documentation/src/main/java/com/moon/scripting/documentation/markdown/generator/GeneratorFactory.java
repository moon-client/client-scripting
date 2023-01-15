package com.moon.scripting.documentation.markdown.generator;

import com.moon.scripting.documentation.markdown.generator.type.DataClassGenerator;
import com.moon.scripting.documentation.markdown.generator.type.EnumGenerator;
import com.moon.scripting.documentation.markdown.generator.type.FunctionGenerator;
import com.moon.scripting.documentation.markdown.generator.type.TableInfoGenerator;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory for creating new {@link Generator} instances
 *
 * @author Lennox
 * @since 1.0.0
 */
public class GeneratorFactory {
    private static final Map<GeneratorType, Generator> GENERATOR_CACHE = new HashMap<>() {{
        put(GeneratorType.DATA, new DataClassGenerator());
        put(GeneratorType.ENUM, new EnumGenerator());
        put(GeneratorType.FUNCTION, new FunctionGenerator());
        put(GeneratorType.TABLE_INFO, new TableInfoGenerator());
    }};

    /**
     * Creates a new generator for the given generator type
     *
     * @param type The generator type
     * @return The generator instance
     * @since 1.0.0
     */
    public static Generator createGenerator(GeneratorType type) {
        return GENERATOR_CACHE.get(type);
    }
}
