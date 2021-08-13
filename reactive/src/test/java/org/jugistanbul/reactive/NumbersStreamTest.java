package org.jugistanbul.reactive;

import io.helidon.common.reactive.Multi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 12.08.2021
 */
class NumbersStreamTest {

    @BeforeAll
    static void subscriptionTest(){

        Multi<Integer> oddNumbers = NumbersStream.naturalNumbers(NumbersStream.oddNumberPredicate);
        oddNumbers.subscribe(NumbersStream.oddNumberSubscriber);

        Multi<Integer> evenNumbers = NumbersStream.naturalNumbers(NumbersStream.evenNumberPredicate);
        evenNumbers.subscribe(NumbersStream.evenNumberSubscriber);
    }

    @Test
    void oddNumbersTest() throws ExecutionException, InterruptedException {

        List<Integer> oddNumbers = NumbersStream
                .naturalNumbers(NumbersStream.oddNumberPredicate)
                .collectList().get();
        oddNumbers.stream().forEach(n -> assertEquals(true, n % 2 == 1));
    }

    @Test
    void evenNumbersTest() throws ExecutionException, InterruptedException {

        List<Integer> oddNumbers = NumbersStream
                .naturalNumbers(NumbersStream.evenNumberPredicate)
                .collectList().get();
        oddNumbers.stream().forEach(n -> assertEquals(true, n % 2 != 1));
    }

    @Test
    void squareRootsTest() throws ExecutionException, InterruptedException {

        Multi<Integer> oddNumbers = NumbersStream.naturalNumbers(NumbersStream.oddNumberPredicate);
        Multi<Integer> evenNumbers = NumbersStream.naturalNumbers(NumbersStream.evenNumberPredicate);

        Multi<Integer> concatenatedNumbers = Multi.concat(oddNumbers, evenNumbers);
        List<String> squareRoots = NumbersStream.transformer(concatenatedNumbers,
                        number -> number.map(n -> String.format("Square of %s is %s", n, (int) Math.pow(n, 2))))
                .collectList().get();

        concatenatedNumbers.collectList().get().stream()
                .forEach(number -> {
                    var expected = String.format("Square of %s is %s", number, (int) Math.pow(number, 2));
                    assertEquals(true, squareRoots.contains(expected));
                });
    }
}
