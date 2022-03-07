#!/usr/bin/env bash

mkdir -p ../modularjars

jar --create --file ../modularjars/health@1.0.jar \
--module-version=1.0 \
--main-class=org.jugistanbul.healthcheck.HealthCheckService \
-C ../mods/health.check .
