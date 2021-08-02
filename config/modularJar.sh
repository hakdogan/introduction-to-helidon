#!/usr/bin/env bash

mkdir -p ../modularjars

jar --create --file ../modularjars/config@1.0.jar \
--module-version=1.0 \
--main-class=org.jugistanbul.config.ConfigService \
-C ../mods/config .
