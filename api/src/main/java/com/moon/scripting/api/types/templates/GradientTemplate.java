package com.moon.scripting.api.types.templates;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;
import lombok.ToString;

@ToString
@DataClassContainer(
    value = "template/gradient",
    path = "types/template/gradient"
)
@Description("Contains information about a gradient to be drawn")
public class GradientTemplate implements RenderTemplate {
    public final float x, y, width, height;
    public final int from, to;
    public final boolean vertical;

    public GradientTemplate(float x, float y, float width, float height, int from, int to, boolean vertical) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.from = from;
        this.to = to;
        this.vertical = vertical;
    }
}
