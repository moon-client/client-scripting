package com.moon.scripting.api.functions;

import com.moon.scripting.api.enums.Facing;
import com.moon.scripting.api.types.Vector2f;
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

    @Description("Sets the players client-side yaw")
    void set_yaw(
        @Parameter("yaw")
        @Description("The new yaw")
        float yaw
    );

    @Description("Sets the players client-side pitch")
    void set_pitch(
        @Parameter("pitch")
        @Description("The new pitch")
        float pitch
    );

    @Description("Sets the players sprinting state")
    void set_sprinting(
        @Parameter("sprinting")
        @Description("The new sprinting state")
        boolean sprinting
    );

    @Description("Makes the player jump")
    void jump();

    @Description("Clicks the left mouse button")
    void left_click();

    @Description("Clicks the right mouse button")
    void right_click();

    @Description("Swings the players item")
    void swing_item();

    @Description("Returns the current forward and strafe the player is moving at")
    Vector2f input();

    @Description("Returns the current players yaw")
    float get_yaw();

    @Description("Returns the current players pitch")
    float get_pitch();

    @Description("Returns the last players yaw")
    float get_prev_yaw();

    @Description("Returns the last players pitch")
    float get_prev_pitch();

    @Description("Returns if the player is on ground")
    boolean is_on_ground();

    @Description("Returns if the player is sprinting")
    boolean is_sprinting();

    @Description("Returns if the player is using the item")
    boolean is_using_item();

    @Description("Returns if the player is blocking")
    boolean is_blocking();

    @Description("Returns if the player is in water")
    boolean is_in_water();

    @Description("Returns if the player is in lava")
    boolean is_in_lava();

    @Description("Returns if the player is burning")
    boolean is_burning();

    @Description("Returns if the player is sneaking")
    boolean is_sneaking();

    @Description("Returns if the player is in a cobweb")
    boolean is_in_cobweb();

    @Description("Returns if the player is dead")
    boolean is_dead();

    @Description("Returns if the player is riding a vehicle")
    boolean is_riding();

    @Description("Returns if the player is on a ladder")
    boolean is_on_ladder();

    @Description("Returns if the player is currently colliding with anything vertically")
    boolean collided_vertically();

    @Description("Returns if the player is currently colliding with anything horizontally")
    boolean collided_horizontally();

    @Description("Returns the players current ticks existed")
    int get_ticks_existed();

    @Description("Returns the players current speed")
    double get_speed();

    @Description("Returns the base move speed")
    double get_base_move_speed();

    @Description("Returns the players current fall distance")
    double get_fall_distance();

    @Description("Returns the players current health")
    double get_health();

    @Description("Returns the players max health")
    double get_max_health();

    @Description("Returns the players current absorption hearts")
    double get_absorption();

    @Description("Returns the players current food amount")
    double get_food_amount();

    @Description("Returns the players current hurt-time")
    int get_hurt_time();

    @Description("Returns the players currently held item")
    int get_held_item_slot();

    @Description("Returns the players current name")
    String get_name();

    @Description("Returns the players entity id")
    int get_id();

    @Description("Returns the players facing<br>For reference please click [here](../enums/facing.md)")
    Facing get_facing();
}
