# Helloworld

Helloworld module shows `Helidon WebServer` usage. The WebServer object represents an immutably configured WEB server and provides a basic lifecycle and monitoring API.

```java
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
```

```bash
➜  ~ http GET http://localhost:8080/api
HTTP/1.1 200 OK
Content-Type: text/plain
Date: Fri, 30 Jul 2021 20:25:10 +0300
connection: keep-alive
content-length: 22

Hello from Helidon SE!

➜  ~ http GET http://localhost:8080/api/hakdogan
HTTP/1.1 200 OK
Content-Type: text/plain
Date: Fri, 30 Jul 2021 20:26:37 +0300
connection: keep-alive
content-length: 14

Hello hakdogan
```

## Requirements
* JDK 11 or later

## To compile
```bash
sh compile.sh
```

## To create modular jar
```bash
sh modularJar.sh
```

## To run
```bash
java -p ../modularjars:../target/libs -m helloworld
```
Or

```bash
sh run.sh
```

