package com.moon.scripting.documentation.markdown.processor;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.EnumContainer;
import com.moon.scripting.documentation.annotation.FunctionContainer;
import com.moon.scripting.documentation.annotation.Parameter;
import com.moon.scripting.documentation.annotation.TableInfoContainer;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains mappings for all documentation annotations
 *
 * @author Lennox
 * @since 1.0.0
 */
public class AnnotationMappings {
    private static final Map<Class<? extends Annotation>, String> ANNOTATION_MAP = new HashMap<>() {{
        put(DataClassContainer.class, "Lcom/moon/scripting/documentation/annotation/DataClassContainer;");
        put(Description.class, "Lcom/moon/scripting/documentation/annotation/Description;");
        put(EnumContainer.class, "Lcom/moon/scripting/documentation/annotation/EnumContainer;");
        put(FunctionContainer.class, "Lcom/moon/scripting/documentation/annotation/FunctionContainer;");
        put(Parameter.class, "Lcom/moon/scripting/documentation/annotation/Parameter;");
        put(TableInfoContainer.class, "Lcom/moon/scripting/documentation/annotation/TableInfoContainer;");
    }};

    /**
     * Returns desc mappings for a given annotation
     *
     * @param annotation The annotation
     * @return The annotation desc or null
     * @since 1.0.0
     */
    public static String getMappingsFor(Class<? extends Annotation> annotation) {
        return ANNOTATION_MAP.get(annotation);
    }
}
