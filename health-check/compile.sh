#!/usr/bin/env bash

#make sure the webserver module is compiled before running this script

javac -d ../mods/health.check \
--module-path ../mods:../target/libs \
src/main/java/module-info.java \
src/main/java/org/jugistanbul/healthcheck/HealthCheckService.java
