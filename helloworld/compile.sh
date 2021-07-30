#!/usr/bin/env bash

mvn clean install

cp src/main/resources/application.yaml ../mods/helloworld/application.yaml

javac -d ../mods/helloworld \
--module-path ../mods:../target/libs \
src/main/java/module-info.java \
src/main/java/org/jugistanbul/helloworld/Starter.java \
src/main/java/org/jugistanbul/helloworld/service/HelloWorldService.java

