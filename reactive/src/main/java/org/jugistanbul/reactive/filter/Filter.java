package org.jugistanbul.reactive.filter;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.logging.Logger;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 10.08.2021
 */
public class Filter<T, R> extends SubmissionPublisher<R> implements Flow.Processor<T, R>
{

    private Flow.Subscription subscription;
    private final Predicate<T> predicate;
    private final String name;

    private static final Logger LOGGER = Logger.getLogger(Filter.class.getName());

    public Filter(final String name, final Predicate<T> predicate){
        super();
        this.name = name;
        this.predicate = predicate;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(predicate.test(item)){
            LOGGER.info(String.format("%s was passed by %s on %s thread", item, name, Thread.currentThread().getName()));
            submit((R) item);
        } else {
            LOGGER.info(String.format("%s was filtered by %s on %s thread", item, name, Thread.currentThread().getName()));
        }
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        close();
    }
}
