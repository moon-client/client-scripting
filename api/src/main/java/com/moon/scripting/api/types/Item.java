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
    private String name;
    @Description("The display name of the item")
    private String display_name;
    @Description("The maximum stack size (usually 64)")
    private int max_stack_size;
    @Description("The current stack size")
    private int stack_size;
    @Description("The max item damage before it breaks")
    private int max_damage;
    @Description("The current item damage")
    private int damage;
    @Description("The enchantments applied on the item")
    private Enchantment[] enchantments;
}
