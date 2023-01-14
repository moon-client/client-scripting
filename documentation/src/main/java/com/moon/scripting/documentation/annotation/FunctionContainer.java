package com.moon.scripting.documentation.annotation;

/**
 * Every class which contains functions for the scripting api has to use this annotation
 * <p>
 * The documentation generator will only generate documentation for a function class if this annotation is used,
 * otherwise it is ignored.
 *
 * @since 1.0.0
 * @author Lennox
 */
public @interface FunctionContainer {
}
