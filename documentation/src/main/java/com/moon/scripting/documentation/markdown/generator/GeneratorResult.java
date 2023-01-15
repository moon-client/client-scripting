package com.moon.scripting.documentation.markdown.generator;

import lombok.Builder;
import lombok.Getter;

/**
 * Contains all information provided by a generator after running
 *
 * @author Lennox
 * @since 1.0.0
 */
@Getter
@Builder
public class GeneratorResult {
    private final String markdown;
    private final String path;
}
