#!/usr/bin/env bash

javac -d ../mods/config \
--module-path ../mods:../target/libs \
src/main/java/module-info.java \
src/main/java/org/jugistanbul/config/ConfigService.java

cp -R conf ../mods/config/conf
cp src/main/resources/application.yaml ../mods/config/application.yaml
cp src/main/resources/config.properties ../mods/config/config.properties