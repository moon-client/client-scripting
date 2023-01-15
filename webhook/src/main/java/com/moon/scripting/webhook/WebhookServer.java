package com.moon.scripting.webhook;

import io.javalin.Javalin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WebhookServer {
    private static final String SECRET_URL = "XfLGi82v0RAytEmryMZ4CEsOrz5O7VW6";

    public static void main(String[] args) {
        Javalin javalin = Javalin.create();
        javalin.post(SECRET_URL, ctx -> {
            System.out.println("Deploying...");
            Process process = Runtime.getRuntime().exec("./deploy.sh");
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println("LOG:" + line);
            }
            System.out.println("Deployed");
            ctx.html("");
        });
        javalin.start(42069);
    }
}
