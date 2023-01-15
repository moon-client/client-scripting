package com.moon.scripting.documentation.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Every method parameter for the scripting api has to use this annotation to receive a proper name.
 * <p>
 * The documentation generator will only generate the parameter name if the annotation is present.
 *
 * @author Lennox
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {
    /**
     * The name of the parameter (we have to do this unfortunately as java does not save method parameter names)
     *
     * @return The parameter name
     * @since 1.0.0
     */
    String value();
}
