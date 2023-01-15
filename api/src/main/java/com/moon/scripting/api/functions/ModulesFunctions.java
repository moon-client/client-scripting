package com.moon.scripting.api.functions;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.FunctionContainer;
import com.moon.scripting.documentation.annotation.Parameter;
import org.luaj.vm2.LuaTable;

@FunctionContainer(
    value = "modules",
    path = "functions/modules"
)
@Description("Contains functions to create modules and settings for them")
public interface ModulesFunctions {
    @Description("Used to register new modules in the client")
    void register(
        @Parameter("name")
        @Description("The name of the module")
        String name,
        @Parameter("display_name")
        @Description("The display name of the module, used in arraylist and clickgui")
        String displayName,
        @Parameter("table")
        @Description("The module table containing all event hooks, you can find all events [here](../tables/events)")
        LuaTable table
    );
}
