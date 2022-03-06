package org.jugistanbul.healthcheck;

import io.helidon.common.reactive.Single;
import io.helidon.health.HealthSupport;
import io.helidon.health.checks.HealthChecks;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 6.03.2022
 ***/
public class HealthCheck
{
    private static final int HTTP_PORT = 8080;
    private static final Logger LOGGER = Logger.getLogger(HealthCheck.class.getName());

    public static void main(String[] args) {

        Single<WebServer> webServer = WebServer
                .builder(createRouting())
                .port(HTTP_PORT)
                .build()
                .start();

        webServer.thenAccept(ws -> {
            LOGGER.info(String.format("Web server is up! http://localhost:%s", ws.port()));
            ws.whenShutdown().thenRun(() -> LOGGER.info("Web server is Down. Good bye!"));
        }).exceptionallyAccept(t -> LOGGER.log(Level.SEVERE, String.format("Startup failed: %s", t.getMessage())));

    }

    private static Routing createRouting() {

        var health = HealthSupport.builder()
                .addLiveness(HealthChecks.healthChecks())
                .build();

        return Routing.builder().register(health).build();
    }
}
