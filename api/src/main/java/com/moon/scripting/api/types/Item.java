package com.moon.scripting.api.types;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;
import lombok.ToString;

@ToString
@DataClassContainer(
    value = "item",
    path = "types/item"
)
@Description("Contains information about an item stack")
public class Item {
    @Description("The item name (locale)")
    public String name;
    @Description("The display name of the item")
    public String display_name;
    @Description("The maximum stack size (usually 64)")
    public int max_stack_size;
    @Description("The current stack size")
    public int stack_size;
    @Description("The max item damage before it breaks")
    public int max_damage;
    @Description("The current item damage")
    public int damage;
    @Description("Returns whether the block is solid if the item is a block. Will always be false otherwise")
    public boolean solid;
    @Description("The enchantments applied on the item")
    public Enchantment[] enchantments;
    @Description("The potion effects applied on the item")
    public Potion[] potions;
}
