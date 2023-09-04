package com.moon.scripting.api.types.templates;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(
    value = "gradient",
    path = "types/template/gradient"
)
@Description("Contains information about a gradient to be drawn")
public class GradientTemplate implements RenderTemplate {
}
