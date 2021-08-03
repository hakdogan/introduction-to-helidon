package org.jugistanbul.config;

import io.helidon.config.Config;
import io.helidon.webclient.WebClient;
import io.helidon.webserver.WebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.json.JsonObject;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 3.08.2021
 */
public class ConfigServiceTest
{
    private static WebServer webServer;
    private static WebClient webClient;
    private static Config config;

    private static final String HOST = "http://localhost";
    private static final String BASE_PATH = "/config";
    private static final String WELCOME_MESSAGE = "Hello from config module!";

    @BeforeAll
    public static void startTheServer() {

        config = ConfigService.buildConfig();
        webServer = ConfigService.startWebServer(config).await();

        webClient = WebClient.builder()
                .addMediaSupport(ConfigService.getMediaType(config))
                .baseUri(String.join(":", HOST, String.valueOf(webServer.port())))
                .build();
    }

    @AfterAll
    public static void stopServer() throws Exception {
        if (webServer != null) {
            webServer.shutdown()
                    .toCompletableFuture()
                    .get(10, TimeUnit.SECONDS);
        }
    }

    @Test
    void welcomeMessageTest() {

        var response = webClient.get()
                .path(BASE_PATH)
                .request(String.class)
                .await();

        assertEquals(WELCOME_MESSAGE, response);
    }

    @Test
    void pathParamTest() {

        var name = "hakdogan";
        var response = webClient.get()
                .path(String.format(String.join("/", BASE_PATH, "hello/%s"), name))
                .request(String.class)
                .await();

        var expectedMessage = String.format("Hello %s", name);
        assertEquals(expectedMessage, response);
    }

    @Test
    void jsonResponseTest() {

        var msg = config.get("app.greeting").asString().orElse("Hello from Helidon SE!");
        var jsonResponse = webClient.get()
                .path(String.join("/", BASE_PATH, "json"))
                .request(JsonObject.class)
                .await();

        assertEquals(msg, jsonResponse.getString("message"));
    }
}
