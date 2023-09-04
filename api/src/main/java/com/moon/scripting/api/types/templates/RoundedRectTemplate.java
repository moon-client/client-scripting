package com.moon.scripting.api.types.templates;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(
    value = "template/rounded_rect",
    path = "types/template/rounded_rect"
)
@Description("Contains information about a rounded_rect to be drawn")
public class RoundedRectTemplate implements RenderTemplate{
}
