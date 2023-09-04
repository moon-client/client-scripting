package com.moon.scripting.api.types.templates;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;
import lombok.ToString;

@ToString
@DataClassContainer(
    value = "template/rounded_rect",
    path = "types/template/rounded_rect"
)
@Description("Contains information about a rounded_rect to be drawn")
public class RoundedRectTemplate implements RenderTemplate {
    public final float x, y, width, height, radius;
    public final int color;

    public RoundedRectTemplate(float x, float y, float width, float height, float radius, int color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.radius = radius;
        this.color = color;
    }
}
