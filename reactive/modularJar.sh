#!/usr/bin/env bash

mkdir -p ../modularjars

jar --create --file ../modularjars/reactive@1.0.jar \
--module-version=1.0 \
--main-class=org.jugistanbul.reactive.NumbersStream \
-C ../mods/reactive .