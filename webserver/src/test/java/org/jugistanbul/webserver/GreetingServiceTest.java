package org.jugistanbul.webserver;

import io.helidon.webclient.WebClient;
import io.helidon.webserver.WebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 30.07.2021
 **/
public class GreetingServiceTest
{
    private static WebServer webServer;
    private static WebClient webClient;

    private static final String HOST = "http://localhost";
    private static final String BASE_PATH = "/api";
    private static final String WELCOME_MESSAGE = "Hello from webserver module!";

    @BeforeAll
    public static void startTheServer() {
        webServer = Starter.startWebServer().await();

        webClient = WebClient.builder()
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
                .path( String.format(String.join("/", BASE_PATH, "hello/%s"), name))
                .request(String.class)
                .await();

        var expectedMessage = String.format("Hello %s", name);
        assertEquals(expectedMessage, response);
    }
}
