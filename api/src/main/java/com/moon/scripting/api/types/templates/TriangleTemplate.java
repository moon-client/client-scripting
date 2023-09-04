package com.moon.scripting.api.types.templates;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(
    value = "triangle",
    path = "types/template/triangle"
)
@Description("Contains information about a triangle to be drawn")
public class TriangleTemplate implements RenderTemplate {
}
