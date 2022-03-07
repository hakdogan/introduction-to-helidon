# Helidon Health Check

Helidon has a set of built-in health checks that can be used by adding the `helidon-health-checks` dependency to the pom file. This module introduces Helidon SE health checks and demonstrates how to create and register health support with web server routing.

```java
private static Routing createRouting() {

        var health = HealthSupport.builder()
                .addLiveness(HealthChecks.healthChecks())
                .addLiveness(() -> HealthCheckResponse.named("LivenessCheck")
                        .up()
                        .withData("time", System.currentTimeMillis())
                        .build())
                .addReadiness(() -> HealthCheckResponse.named("ReadinessCheck")
                        .state (readyTime.get() != 0 )
                        .withData( "time", readyTime.get())
                        .build())
                .build();

        return Routing.builder().register(health).build();
    }
```

```bash
➜  ~ http GET localhost:8080/health
HTTP/1.1 503 Service Unavailable
Content-Type: application/json
Date: Mon, 7 Mar 2022 22:04:25 +0300
connection: keep-alive
transfer-encoding: chunked

{
    "checks": [
        {
            "data": {
                "time": 1646679865987
            },
            "name": "LivenessCheck",
            "state": "UP",
            "status": "UP"
        },
        {
            "data": {
                "time": 0
            },
            "name": "ReadinessCheck",
            "state": "DOWN",
            "status": "DOWN"
        },
        {
            "name": "deadlock",
            "state": "UP",
            "status": "UP"
        },
        {
            "data": {
                "free": "7.89 GB",
                "freeBytes": 8475168768,
                "percentFree": "7.00%",
                "total": "112.80 GB",
                "totalBytes": 121123069952
            },
            "name": "diskSpace",
            "state": "UP",
            "status": "UP"
        },
        {
            "data": {
                "free": "106.87 MB",
                "freeBytes": 112063920,
                "max": "2.00 GB",
                "maxBytes": 2147483648,
                "percentFree": "98.87%",
                "total": "130.00 MB",
                "totalBytes": 136314880
            },
            "name": "heapMemory",
            "state": "UP",
            "status": "UP"
        }
    ],
    "outcome": "DOWN",
    "status": "DOWN"
}

➜  ~ http GET localhost:8080/health
HTTP/1.1 200 OK
Content-Type: application/json
Date: Mon, 7 Mar 2022 22:05:07 +0300
connection: keep-alive
content-length: 621

{
    "checks": [
        {
            "data": {
                "time": 1646679907131
            },
            "name": "LivenessCheck",
            "state": "UP",
            "status": "UP"
        },
        {
            "data": {
                "time": 1646679868155
            },
            "name": "ReadinessCheck",
            "state": "UP",
            "status": "UP"
        },
        {
            "name": "deadlock",
            "state": "UP",
            "status": "UP"
        },
        {
            "data": {
                "free": "7.89 GB",
                "freeBytes": 8474959872,
                "percentFree": "7.00%",
                "total": "112.80 GB",
                "totalBytes": 121123069952
            },
            "name": "diskSpace",
            "state": "UP",
            "status": "UP"
        },
        {
            "data": {
                "free": "101.73 MB",
                "freeBytes": 106672536,
                "max": "2.00 GB",
                "maxBytes": 2147483648,
                "percentFree": "98.62%",
                "total": "130.00 MB",
                "totalBytes": 136314880
            },
            "name": "heapMemory",
            "state": "UP",
            "status": "UP"
        }
    ],
    "outcome": "UP",
    "status": "UP"
}

➜  ~ http GET localhost:8080/health/live
HTTP/1.1 200 OK
Content-Type: application/json
Date: Mon, 7 Mar 2022 22:06:11 +0300
connection: keep-alive
content-length: 537

{
    "checks": [
        {
            "data": {
                "time": 1646681015071
            },
            "name": "LivenessCheck",
            "state": "UP",
            "status": "UP"
        },
        {
            "name": "deadlock",
            "state": "UP",
            "status": "UP"
        },
        {
            "data": {
                "free": "7.83 GB",
                "freeBytes": 8404127744,
                "percentFree": "6.94%",
                "total": "112.80 GB",
                "totalBytes": 121123069952
            },
            "name": "diskSpace",
            "state": "UP",
            "status": "UP"
        },
        {
            "data": {
                "free": "99.21 MB",
                "freeBytes": 104028856,
                "max": "2.00 GB",
                "maxBytes": 2147483648,
                "percentFree": "98.50%",
                "total": "130.00 MB",
                "totalBytes": 136314880
            },
            "name": "heapMemory",
            "state": "UP",
            "status": "UP"
        }
    ],
    "outcome": "UP",
    "status": "UP"
}


➜  ~ http GET localhost:8080/health/ready
HTTP/1.1 200 OK
Content-Type: application/json
Date: Mon, 7 Mar 2022 22:06:46 +0300
connection: keep-alive
content-length: 124

{
    "checks": [
        {
            "data": {
                "time": 1646680961174
            },
            "name": "ReadinessCheck",
            "state": "UP",
            "status": "UP"
        }
    ],
    "outcome": "UP",
    "status": "UP"
}
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
java -p ../modularjars:../target/libs:../mods -m health.check
```
Or

```bash
sh run.sh
```

