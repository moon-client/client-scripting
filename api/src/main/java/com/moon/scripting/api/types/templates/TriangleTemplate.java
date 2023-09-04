package com.moon.scripting.api.types.templates;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;
import lombok.ToString;

@ToString
@DataClassContainer(
    value = "template/triangle",
    path = "types/template/triangle"
)
@Description("Contains information about a triangle to be drawn")
public class TriangleTemplate implements RenderTemplate {
    public final float top_x, top_y, left_x, left_y, right_x, right_y;
    public final int color;

    public TriangleTemplate(
        float top_x,
        float top_y,
        float left_x,
        float left_y,
        float right_x,
        float right_y,
        int color
    ) {
        this.top_x = top_x;
        this.top_y = top_y;
        this.left_x = left_x;
        this.left_y = left_y;
        this.right_x = right_x;
        this.right_y = right_y;
        this.color = color;
    }
}
