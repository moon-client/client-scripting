package com.moon.scripting.documentation.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Every class which is a data class has to be annotated with this annotation
 * <p>
 * The documentation generator will only generate documentation for a data class if this annotation is used.
 *
 * @author Lennox
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DataClassContainer {
    /**
     * Returns the name of the data class container
     *
     * @return The name
     * @since 1.0.0
     */
    String value();

    /**
     * Returns the path of the data class container
     *
     * @return The path
     * @since 1.0.0
     */
    String path();
}
