package com.moon.scripting.api.functions;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.FunctionContainer;
import com.moon.scripting.documentation.annotation.Parameter;
import org.luaj.vm2.LuaTable;

@FunctionContainer(
    value = "player",
    path = "functions/player"
)
@Description("Contains functions to control the local player")
public interface PlayerFunctions {
    @Description("Makes the player jump")
    void jump();

    @Description("Clicks the left mouse button")
    void left_click();

    @Description("Clicks the right mouse button")
    void right_click();

    @Description("Swings the players item")
    void swing_item();

    @Description("Returns if the player is on ground")
    boolean is_on_ground();

    @Description("Sets the speed of the player using the move event")
    void set_speed(
        @Parameter("event")
        @Description("The move event (if you want to set the speed outside of the move event, use **set_motion_speed**)")
        LuaTable event,
        @Parameter("speed")
        @Description("The speed to move with")
        double speed
    );

    @Description("Sets the speed of the player using motions")
    void set_motion_speed(
        @Parameter("speed")
        @Description("The speed to move with")
        double speed
    );
}
