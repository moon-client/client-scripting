package com.moon.scripting.documentation.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Every class which contains enums for the scripting api has to use this annotation
 * <p>
 * The documentation generator will only generate documentation for an enum class if this annotation is used, otherwise
 * it is ignored.
 *
 * @author Lennox
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumContainer {
    /**
     * Returns the name of the enum container
     *
     * @return The name
     * @since 1.0.0
     */
    String value();

    /**
     * Returns the path of the enum container
     *
     * @return The path
     * @since 1.0.0
     */
    String path();
}
