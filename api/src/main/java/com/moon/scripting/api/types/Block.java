package com.moon.scripting.api.types;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;
import lombok.ToString;

@ToString
@DataClassContainer(
    value = "block",
    path = "types/block"
)
@Description("Contains information about a block retrieved from the local world")
public class Block {
    @Description("The name of the block")
    public String name;
    @Description("Whether the block has a full bounding box or not")
    public boolean fullBlock;
    @Description("Whether the block is translucent or not (used while rendering)")
    public boolean translucent;
    @Description("The hardness of the block")
    public float blockHardness;
    @Description("The slipperiness of the block")
    public float slipperiness;

    public Block(String name, boolean fullBlock, boolean translucent, float blockHardness, float slipperiness) {
        this.name = name;
        this.fullBlock = fullBlock;
        this.translucent = translucent;
        this.blockHardness = blockHardness;
        this.slipperiness = slipperiness;
    }

    public Block() {
    }
}
