# Rendering

Trying to render something properly can be complicated, here are some examples and things to know when working with the
rendering api.

## Important things to know

The overlay render event has a pre and post state, please make sure you only render on one of those two states,
otherwise you render your stuff twice which can cause a major decrease in FPS depending on the amount of render passes.
<br><br>
Please always refer to using the batched renderer, it allows you to render multiple elements of the **same type** with
ease. You **CANNOT** render two different types in one render batch. You also cannot render two different font types in
one render batch, different sizes are fine.
<br><br>
The minecraft font is only available in the immediate renderer, this is the only exception where the immediate renderer
should be used.

## Basic Batch Example

Below you can find a basic example for rendering the same type in a batched format to save fps by not causing more than
one render pass.

```lua
render.batched.render_batch(
    enums.template_type.RECT,
    {
        -- We use hex colors for rendering, they MUST always contain an alpha component
        -- e.g 0xFFFF0000
        types.template.rect.new(0, 0, 100, 100, -1),
        types.template.rect.new(100, 0, 100, 100, 0xFF00FF00)
    }
)
```

This example renders **two rectangles** in **one** render pass saving a lot of fps. You can add practically infinite
elements to one render pass (the amount actually varies by the amount of GPU V-RAM available on the host system,
usually you should never notice any limits as this is just basic 2d rendering)