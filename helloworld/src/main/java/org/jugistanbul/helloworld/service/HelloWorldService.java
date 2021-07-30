package org.jugistanbul.helloworld.service;

import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 30.07.2021
 **/
public class HelloWorldService implements Service
{

    public HelloWorldService(final Config config) { }

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/", this::welcomeMessageHandler)
                .get("/{name}", this::pathParamHandler);
    }

    private void welcomeMessageHandler(final ServerRequest request, final ServerResponse response) {
        response.send("Hello from Helidon SE!");
    }

    private void pathParamHandler(final ServerRequest request, final ServerResponse response) {
        var name = request.path().param("name");
        response.send(String.format("Hello %s", name));
    }
}
