#!/usr/bin/env bash

mkdir -p ../modularjars

jar --create --file ../modularjars/greeting@1.0.jar \
--module-version=1.0 \
--main-class=org.jugistanbul.webserver.Starter \
-C ../mods/greeting .
