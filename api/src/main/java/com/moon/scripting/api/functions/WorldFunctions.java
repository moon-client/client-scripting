package com.moon.scripting.api.functions;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.FunctionContainer;

@FunctionContainer(
    value = "world",
    path = "functions/world"
)
@Description("Contains functions to control the local world")
public interface WorldFunctions {
    @Description("Returns an array of all loaded entity ids")
    int[] entities();

    @Description("Returns an array of all loaded player ids")
    int[] players();

    @Description("Returns the biome of the current world")
    String biome();
}
