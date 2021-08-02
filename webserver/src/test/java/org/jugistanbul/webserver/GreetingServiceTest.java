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

    @BeforeAll
    public static void startTheServer() {
        webServer = Starter.startWebServer().await();

        webClient = WebClient.builder()
                .baseUri("http://localhost:" + webServer.port())
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
                .path("/api")
                .request(String.class)
                .await();

        assertEquals("Hello from helloworld module!", response);
    }

    @Test
    void pathParamTest() {

        var name = "hakdogan";
        var response = webClient.get()
                .path(String.format("/api/hello/%s", name))
                .request(String.class)
                .await();

        var expectedMessage = String.format("Hello %s", name);
        assertEquals(expectedMessage, response);
    }
}
