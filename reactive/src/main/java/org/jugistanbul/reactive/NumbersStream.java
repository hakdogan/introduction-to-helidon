package org.jugistanbul.reactive;

import io.helidon.common.reactive.Multi;
import org.jugistanbul.reactive.filter.Filter;
import org.jugistanbul.reactive.subscriber.Subscriber;
import org.jugistanbul.reactive.transformer.Transformer;

import java.util.concurrent.*;
import java.util.logging.Logger;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 10.08.2021
 */
public class NumbersStream
{
    static final SubmissionPublisher<Integer> oddNumberPublisher = new SubmissionPublisher<>();
    static final Subscriber<Integer> oddNumberSubscriber = new Subscriber<>("Odd Number Subscriber");
    static final Filter<Integer, Integer> oddNumberFilter = new Filter<>("Odd Number Filter", n -> n % 2 == 1);
    static final Transformer<Integer, Integer> oddNumberTransformer = new Transformer<>("Odd Number Transformer", NumbersStream::transformGivenNumber);

    static final SubmissionPublisher<Integer> evenNumberPublisher = new SubmissionPublisher<>();
    static final Subscriber<Integer> evenNumberSubscriber = new Subscriber<>("Even Number Subscriber");
    static final Filter<Integer, Integer> evenNumberFilter = new Filter<>("Even Number Filter", n -> n % 2 != 1);
    static final Transformer<Integer, Integer> evenNumberTransformer = new Transformer<>("Even Number Transformer", NumbersStream::transformGivenNumber);

    private static final Logger LOGGER = Logger.getLogger(NumbersStream.class.getName());

    public static void main(String[] args) throws InterruptedException {

        LOGGER.info("Items are being pushed...");

        Multi.concat(oddNumberTransformer, evenNumberTransformer)
                .forEach(item -> LOGGER.info(String.format("%s consumed", item)));

        oddNumberPusher();
        TimeUnit.SECONDS.sleep(15);
        oddNumberPublisher.close();

        evenNumberPusher();
        TimeUnit.SECONDS.sleep(15);
        evenNumberPublisher.close();
    }

    private static Integer transformGivenNumber(final Integer number) {
        return number * ThreadLocalRandom.current().nextInt(1, 1000);
    }

    private static Multi<Integer> sourceNumber() {
        return Multi.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    private static CompletableFuture<Void> oddNumberPusher() {
        oddNumberPublisher.subscribe(oddNumberFilter);
        oddNumberFilter.subscribe(oddNumberTransformer);
        oddNumberTransformer.subscribe(oddNumberSubscriber);
        return runner(oddNumberPublisher);
    }

    private static CompletableFuture<Void> evenNumberPusher() {
        evenNumberPublisher.subscribe(evenNumberFilter);
        evenNumberFilter.subscribe(evenNumberTransformer);
        evenNumberTransformer.subscribe(evenNumberSubscriber);
        return runner(evenNumberPublisher);
    }

    private static <T> CompletableFuture<Void> runner(final SubmissionPublisher<T> publisher){
        return CompletableFuture.runAsync(() -> {
            sourceNumber().subscribe(item -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1, 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publisher.submit((T) item);
            });
        });
    }
}
