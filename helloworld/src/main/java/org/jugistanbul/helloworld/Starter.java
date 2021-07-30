package org.jugistanbul.helloworld;

import io.helidon.common.reactive.Single;
import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;
import org.jugistanbul.helloworld.service.HelloWorldService;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 30.07.2021
 **/
public class Starter
{

    private static final Logger LOGGER = Logger.getLogger(Starter.class.getName());

    public static void main(String[] args) {
        startWebServer();
    }

    static Single<WebServer> startWebServer(){

        var config = Config.create();
        var service = new HelloWorldService(config);

        Single<WebServer> webServer = WebServer.builder(Routing.builder().register("/api", service)
                .build()).config(config.get("server")).build().start();

        webServer.thenAccept(ws -> {
            LOGGER.info("Web server is up! http://localhost:" + ws.port() + "/api");
            ws.whenShutdown().thenRun(() -> LOGGER.info("Web server is Down. Good bye!"));
        }).exceptionallyAccept(t -> {
            LOGGER.log(Level.SEVERE, "Startup failed: " + t.getMessage());
        });

        return webServer;
    }
}
