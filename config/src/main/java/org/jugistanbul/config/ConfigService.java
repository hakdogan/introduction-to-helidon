package org.jugistanbul.config;

import io.helidon.common.reactive.Single;
import io.helidon.config.Config;
import io.helidon.media.common.MediaSupport;
import io.helidon.media.jsonb.JsonbSupport;
import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;
import org.jugistanbul.webserver.service.GreetingService;

import java.util.logging.Level;
import java.util.logging.Logger;

import static io.helidon.config.ConfigSources.*;

public final class ConfigService
{
    private static final Logger LOGGER = Logger.getLogger(ConfigService.class.getName());

    public static void main(String[] args) {
        Config config = buildConfig();
        startWebServer(config);
    }

    static Single<WebServer> startWebServer(final Config config) {

        var service = new GreetingService(config);
        Single<WebServer> webServer = WebServer
                .builder(Routing.builder()
                        .register("/config", service)
                        .build())
                .config(config.get("server"))
                .addMediaSupport(getMediaType(config))
                .build().start();

        webServer.thenAccept(ws -> {
            LOGGER.info(String.format("Web server is up! http://localhost:%s/config", ws.port()));
            ws.whenShutdown().thenRun(() -> LOGGER.info("Web server is Down. Good bye!"));
        }).exceptionallyAccept(t -> LOGGER.log(Level.SEVERE, String.format("Startup failed: %s", t.getMessage())));

        return webServer;
    }

    static Config buildConfig() {
        return Config.builder()
                .disableEnvironmentVariablesSource()
                .addSource(directory("conf"))
                .addSource(classpath("config.properties"))
                .addSource(classpath("constants.properties").optional())
                .addSource(file("src/main/resources/application.yaml"))
                .build();
    }

    static MediaSupport getMediaType(final Config config) {
        return switch (config.get("mediaType").asString().orElse("")) {
            case "jsonp" -> JsonpSupport.create();
            default -> JsonbSupport.create();
        };
    }
}
