package com.moon.scripting.api.tables;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.Parameter;
import com.moon.scripting.documentation.annotation.TableInfoContainer;

@TableInfoContainer(
    value = "events",
    path = "tables/events"
)
@Description("Contains all events you can hook when creating a module table.<br>**BEWARE:** If an event has any parameters, you need to add a generalized event argument to your function, NOT THE PARAMETERS THEMSELVES. You can access all the parameters from the generalized accessor!")
public interface EventsTable {
    @Description("Called once the module is enabled")
    void on_enable();

    @Description("Called once the module is disabled")
    void on_disable();

    @Description("Called on client tick (every 0.05s)")
    void on_tick(
        @Parameter("pre")
        @Description("Defines if the event was called pre or post tick")
        boolean pre
    );
}
