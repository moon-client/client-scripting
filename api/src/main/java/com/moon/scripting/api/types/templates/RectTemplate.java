package com.moon.scripting.api.types.templates;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(
    value = "template/rect",
    path = "types/template/rect"
)
@Description("Contains information about a rect to be drawn")
public class RectTemplate implements RenderTemplate {
}
