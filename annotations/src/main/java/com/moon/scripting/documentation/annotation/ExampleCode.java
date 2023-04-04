package com.moon.scripting.documentation.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to create code examples for {@link FunctionContainer} or {@link TableInfoContainer} methods
 *
 * @author Lennox
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExampleCode {
    /**
     * Returns the code snippet to be inserted
     *
     * @return The code snippet
     * @since 1.0.0
     */
    String value();
}
