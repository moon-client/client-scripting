package com.moon.scripting.documentation.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used by the documentation generator to generate descriptions for fields, methods, parameters and similar objects
 *
 * @author Lennox
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Description {
    String value();
}
