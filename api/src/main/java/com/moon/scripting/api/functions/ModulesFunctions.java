package com.moon.scripting.api.functions;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.ExampleCode;
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
    @ExampleCode("""
        local module = {
            on_enable = function()
                client:print('The module has been enabled!')
            end
        }
        -- Register
        modules:register("YourModule", "Your Module", module)""")
    void register(
        @Parameter("name")
        @Description("The name of the module")
        String name,
        @Parameter("display_name")
        @Description("The display name of the module, used in arraylist and clickgui")
        String displayName,
        @Parameter("table")
        @Description("The module table containing all event hooks, you can find all available events [here](../tables/events.md)")
        LuaTable table
    );

    @Description("Used to create a boolean setting for a scripting module")
    void create_boolean_setting(
        @Parameter("module")
        @Description("The module the setting will be added to")
        String module,
        @Parameter("name")
        @Description("The name of the setting")
        String name,
        @Parameter("default")
        @Description("The default value of the setting")
        boolean defaultValue
    );

    @Description("Used to create a integer setting for a scripting module")
    void create_integer_setting(
        @Parameter("module")
        @Description("The module the setting will be added to")
        String module,
        @Parameter("name")
        @Description("The name of the setting")
        String name,
        @Parameter("default")
        @Description("The default value of the setting (ideally in-between of min and max)")
        int defaultValue,
        @Parameter("default")
        @Description("The minimum value allowed")
        int min,
        @Parameter("default")
        @Description("The maximum value allowed")
        int max,
        @Parameter("default")
        @Description("The increment amount")
        int increment
    );

    @Description("Used to create a float setting for a scripting module")
    void create_float_setting(
        @Parameter("module")
        @Description("The module the setting will be added to")
        String module,
        @Parameter("name")
        @Description("The name of the setting")
        String name,
        @Parameter("default")
        @Description("The default value of the setting (ideally in-between of min and max)")
        float defaultValue,
        @Parameter("default")
        @Description("The minimum value allowed")
        float min,
        @Parameter("default")
        @Description("The maximum value allowed")
        float max,
        @Parameter("default")
        @Description("The increment amount")
        float increment
    );

    @Description("Used to create a color setting for a scripting module")
    void create_color_setting(
        @Parameter("module")
        @Description("The module the setting will be added to")
        String module,
        @Parameter("name")
        @Description("The name of the setting")
        String name,
        @Parameter("default")
        @Description("The default value of the setting")
        int defaultValue
    );

    @Description("Used to create a text setting for a scripting module")
    void create_text_setting(
        @Parameter("module")
        @Description("The module the setting will be added to")
        String module,
        @Parameter("name")
        @Description("The name of the setting")
        String name,
        @Parameter("default")
        @Description("The default value of the setting")
        String defaultValue
    );

    @Description("Returns the value of a setting")
    Object get_setting(
        @Parameter("module")
        @Description("The module which contains the setting")
        String module,
        @Parameter("name")
        @Description("The setting name")
        String name
    );
}
