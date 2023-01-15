package com.moon.scripting.api.functions;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.FunctionContainer;
import com.moon.scripting.documentation.annotation.Parameter;

@FunctionContainer(
    value = "client",
    path = "functions/client"
)
@Description("Contains several functions to control and get information from the client")
public interface ClientFunctions {
    @Description("Prints a message into the minecraft chat")
    void print(
        @Parameter("message")
        @Description("The message which is going to be printed")
        String message
    );
}


