package org.jugistanbul.webserver.service;

import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import java.util.Collections;

/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 30.07.2021
 **/
public class GreetingService implements Service
{

    private final Config config;
    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    public GreetingService(final Config config) {
        this.config = config;
    }

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/", this::welcomeMessageHandler)
                .get("/json", this::jsonResponseHandler)
                .get("/hello/{name}", this::pathParamHandler);
    }

    private void welcomeMessageHandler(final ServerRequest request, final ServerResponse response) {
        response.send(config.get("app.greeting").asString().orElse("Hello from Helidon SE!"));
    }

    private void jsonResponseHandler(final ServerRequest request, final ServerResponse response) {
        var msg = config.get("app.greeting").asString().orElse("Hello from Helidon SE!");
        JsonObject returnObject = JSON.createObjectBuilder()
                .add("message", msg)
                .build();
        response.send(returnObject);
    }

    private void pathParamHandler(final ServerRequest request, final ServerResponse response) {
        var name = request.path().param("name");
        response.send(String.format("Hello %s", name));
    }
}
