# Helidon Reactive Engine
Helidon has two handy APIs for working with reactive streams. One of these is for working with `java.util.concurrent.Flow` and this module introduces it.

```java
    static Multi<Integer> naturalNumbers(final Predicate<Integer> predicate) {
        return Multi.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
               .filter(predicate);
    }
```

```java
    static Multi<String> transformer(final Multi<Integer> numbers,
                                     final Function<Multi<Integer>, Multi<String>> func) {
        return numbers.to(func);
    }
```

```java
        Multi<Integer> oddNumbers = naturalNumbers(oddNumberPredicate);
        oddNumbers.subscribe(oddNumberSubscriber);

        Multi<Integer> evenNumbers = naturalNumbers(evenNumberPredicate);
        evenNumbers.subscribe(evenNumberSubscriber);

        Multi.concat(oddNumbers, evenNumbers)
        .compose(number ->
        number.map(n -> String.format("Square of %s is %s", n, (int) Math.pow(n, 2))))
        .subscribe(System.out::println);
```

```text
Odd Number Subscriber got 1
Odd Number Subscriber got 3
Odd Number Subscriber got 5
Odd Number Subscriber got 7
Odd Number Subscriber got 9
Odd Number Subscriber complete!

Even Number Subscriber got 2
Even Number Subscriber got 4
Even Number Subscriber got 6
Even Number Subscriber got 8
Even Number Subscriber got 10
Even Number Subscriber complete!

Square of 1 is 1
Square of 3 is 9
Square of 5 is 25
Square of 7 is 49
Square of 9 is 81
Square of 2 is 4
Square of 4 is 16
Square of 6 is 36
Square of 8 is 64
Square of 10 is 100
```

## Requirements
* JDK 11 or later

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