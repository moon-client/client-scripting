package com.moon.scripting.documentation.annotation;

/**
 * Used to create code examples for {@link FunctionContainer} or {@link TableInfoContainer} methods
 *
 * @author Lennox
 * @since 1.0.0
 */
public @interface ExampleCode {
    /**
     * Returns the code snippet to be inserted
     *
     * @return The code snippet
     * @since 1.0.0
     */
    String value();
}
