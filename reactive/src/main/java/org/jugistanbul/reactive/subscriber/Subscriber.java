package org.jugistanbul.reactive.subscriber;

import java.util.concurrent.Flow;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 10.08.2021
 */
public class Subscriber<T> implements Flow.Subscriber<T>
{
    private Flow.Subscription subscription;
    private final String name;

    private static final Logger LOGGER = Logger.getLogger(Subscriber.class.getName());

    public Subscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item) {

        try {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(800, 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOGGER.info(String.format("%s got %s in %s thread", name, item, Thread.currentThread().getName()));
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        LOGGER.info(String.format("%s complete!", name));
    }
}

