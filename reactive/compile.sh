#!/usr/bin/env bash

javac -d ../mods/reactive \
--module-path target/libs \
src/main/java/module-info.java \
src/main/java/org/jugistanbul/reactive/NumbersStream.java \
src/main/java/org/jugistanbul/reactive/filter/Filter.java \
src/main/java/org/jugistanbul/reactive/subscriber/Subscriber.java \
src/main/java/org/jugistanbul/reactive/transformer/Transformer.java
