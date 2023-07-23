# Vectors

There are some methods in the api which use vectors, now you might ask how to create and use them.<br>
This is a full tutorial on how to create and work with vectors!

## Create a Vector

An important thing you need to know is that there are multiple types of vectors, you can find them under the `types`
category.<br>
After you selected the desired vector you have multiple options, either create an empty one with default values or
predefining what values you want to have on each dimension.

### Code Example

```lua
-- Create a vector with default values (0)
local default = types.vec2f.empty()
local default_x = default.x -- Will be 0 in this case

-- Create a vector with pre-defined values
local defined = types.vec2f.new(5, 10)
local defined_x = defined.x -- Will be 5 in this case

-- Redefining a vectors values
defined.x = 10
local new_defined_x = defined.x -- Will be 10 in this case
```