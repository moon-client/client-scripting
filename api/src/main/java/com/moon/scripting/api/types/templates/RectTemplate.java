package com.moon.scripting.api.types.templates;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;
import lombok.ToString;

@ToString
@DataClassContainer(
    value = "template/rect",
    path = "types/template/rect"
)
@Description("Contains information about a rect to be drawn")
public class RectTemplate implements RenderTemplate {
    public final float x, y, width, height;
    public final int color;

    public RectTemplate(float x, float y, float width, float height, int color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
}
