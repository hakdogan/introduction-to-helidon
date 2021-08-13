package org.jugistanbul.reactive.subscriber;

import java.util.concurrent.Flow;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 10.08.2021
 */
public class Subscriber<T> implements Flow.Subscriber<T>
{
    private Flow.Subscription subscription;
    private final String name;

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
        System.out.println(String.format("%s got %s", name, item));
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println(String.format("%s complete!\n", name));
    }
}

