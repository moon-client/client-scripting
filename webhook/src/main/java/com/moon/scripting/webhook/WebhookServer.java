package com.moon.scripting.webhook;

import io.javalin.Javalin;

public class WebhookServer {
    private static final String SECRET_URL = "XfLGi82v0RAytEmryMZ4CEsOrz5O7VW6";

    public static void main(String[] args) {
        Javalin javalin = Javalin.create();
        javalin.post(SECRET_URL, ctx -> {
            System.out.println("Deploying...");
            Runtime.getRuntime().exec("./deploy.sh");
            System.out.println("Deployed");
            ctx.html("");
        });
        javalin.start(42069);
    }
}
