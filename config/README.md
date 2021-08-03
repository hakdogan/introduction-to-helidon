# Helidon Config

Helidon provides a very flexible configuration system. You can include configuration data from a variety of sources using different formats and customize the precedence of sources. Furthermore, you can make them optional or mandatory. This module introduces Helidon SE configuration and demonstrates the fundamental concepts using several examples.

```java
    private static Config buildConfig() {
        return Config.builder()
                .disableEnvironmentVariablesSource()
                    .addSource(directory("conf"))
                    .addSource(classpath("config.properties"))
                    .addSource(classpath("constants.properties").optional())
                    .addSource(file("src/main/resources/application.yaml"))
                .build();
    }
```

```java
    Single<WebServer> webServer = WebServer
        .builder(Routing.builder()
        .register("/config", service)
        .build())
        .config(config.get("server"))
        .addMediaSupport(getMediaType(config))
        .build().start();
```

```java
    private static MediaSupport getMediaType(final Config config) {
        return switch (config.get("mediaType").asString().orElse("")) {
        case "jsonp" -> JsonpSupport.create();
default -> JsonbSupport.create();
        };
        }
```

```bash
~ http GET http://localhost:8090/config
HTTP/1.1 200 OK
Content-Type: text/plain
Date: Tue, 3 Aug 2021 09:54:47 +0300
connection: keep-alive
content-length: 25

Hello from config module!

➜  ~ http GET http://localhost:8090/config/json
HTTP/1.1 200 OK
Content-Type: application/json
Date: Tue, 3 Aug 2021 09:55:17 +0300
connection: keep-alive
content-length: 39

{
"message": "Hello from config module!"
}
```

## Requirements
* JDK 12 or later

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
java -p ../modularjars:../target/libs:../mods -m config
```
Or

```bash
sh run.sh
```