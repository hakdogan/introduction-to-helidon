#!/usr/bin/env bash

javac -d ../mods/greeting \
--module-path ../mods:../target/libs \
src/main/java/module-info.java \
src/main/java/org/jugistanbul/webserver/Starter.java \
src/main/java/org/jugistanbul/webserver/service/GreetingService.java

cp src/main/resources/application.yaml ../mods/greeting/application.yaml