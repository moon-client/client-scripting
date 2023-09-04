package com.moon.scripting.api.types.render;

import com.moon.scripting.api.types.templates.RenderTemplate;
import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(
    value = "render/render_batch",
    path = "types/render/render_batch"
)
@Description("Contains information about a block retrieved from the local world")
public abstract class RenderBatch {
    @Description("Puts the given render_template into the batch, make sure it matches the type you defined on creation")
    public abstract void add(RenderTemplate template);
}
