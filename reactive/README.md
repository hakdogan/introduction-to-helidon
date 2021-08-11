# Helidon Reactive Engine
Helidon has two handy APIs for working with reactive streams. One of these is for working with `java.util.concurrent.Flow` and this module introduces it.

```java
private static Multi<Integer> sourceNumber() {
    return Multi.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}
```

```java
Multi.concat(oddNumberTransformer, evenNumberTransformer)
                .forEach(item -> LOGGER.info(String.format("%s consumed", item)));
```

## Requirements
* JDK 12 or later

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
java -p ../modularjars:../target/libs:../mods -m reactive
```
Or

```bash
sh run.sh
```