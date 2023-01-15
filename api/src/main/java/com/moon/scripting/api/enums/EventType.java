package com.moon.scripting.api.enums;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.EnumContainer;

@EnumContainer(
    value = "events",
    path = "enums/events"
)
@Description("All event types you can hook using the event system")
public enum EventType {
    @Description("Called at start of tick (20 t/s)")
    TICK
}
