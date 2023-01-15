package com.moon.scripting.api.tables;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.TableInfoContainer;

@TableInfoContainer(
    value = "events",
    path = "tables/events"
)
@Description("Contains all events you can hook when creating a module table")
public interface EventsTable {
    @Description("Called once the module is enabled")
    void on_enable();

    @Description("Called once the module is disabled")
    void on_disable();
}
