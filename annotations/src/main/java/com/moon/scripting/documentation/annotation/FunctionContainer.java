package com.moon.scripting.documentation.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Every class which contains functions for the scripting api has to use this annotation
 * <p>
 * The documentation generator will only generate documentation for a function class if this annotation is used,
 * otherwise it is ignored.
 *
 * @since 1.0.0
 * @author Lennox
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionContainer {
    /**
     * Returns the name of the function container
     *
     * @return The name
     * @since 1.0.0
     */
    String value();

    /**
     * Returns the path of the function container
     *
     * @return The path
     * @since 1.0.0
     */
    String path();
}
