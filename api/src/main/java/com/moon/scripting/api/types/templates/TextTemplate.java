package com.moon.scripting.api.types.templates;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(
    value = "template/text",
    path = "types/template/text"
)
@Description("Contains information about a text to be drawn")
public class TextTemplate implements RenderTemplate {
}
