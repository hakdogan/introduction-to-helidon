package org.jugistanbul.reactive;

import io.helidon.common.reactive.Multi;
import org.jugistanbul.reactive.subscriber.Subscriber;
import java.util.function.Function;
import java.util.function.Predicate;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 10.08.2021
 */
public class NumbersStream
{
    static final Subscriber<Integer> oddNumberSubscriber = new Subscriber<>("Odd Number Subscriber");
    static final Subscriber<Integer> evenNumberSubscriber = new Subscriber<>("Even Number Subscriber");
    static final Predicate<Integer> oddNumberPredicate = n -> n % 2 == 1;
    static final Predicate<Integer> evenNumberPredicate = n -> n % 2 != 1;

    public static void main(String[] args) {

        Multi<Integer> oddNumbers = naturalNumbers(oddNumberPredicate);
        oddNumbers.subscribe(oddNumberSubscriber);

        Multi<Integer> evenNumbers = naturalNumbers(evenNumberPredicate);
        evenNumbers.subscribe(evenNumberSubscriber);

        Multi.concat(oddNumbers, evenNumbers)
                .compose(number ->
                        number.map(n -> String.format("Square of %s is %s", n, (int) Math.pow(n, 2))))
                .subscribe(System.out::println);
    }

    static Multi<Integer> naturalNumbers(final Predicate<Integer> predicate) {
        return Multi.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .filter(predicate);
    }

    static Multi<String> transformer(final Multi<Integer> numbers,
                                     final Function<Multi<Integer>, Multi<String>> func) {
        return numbers.to(func);
    }
}
