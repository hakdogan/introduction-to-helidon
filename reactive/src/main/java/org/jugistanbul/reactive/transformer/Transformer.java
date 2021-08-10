package org.jugistanbul.reactive.transformer;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;
import java.util.logging.Logger;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 10.08.2021
 */
public class Transformer<T, R> extends SubmissionPublisher<R> implements Flow.Processor<T, R>
{

    private Flow.Subscription subscription;
    private final Function function;
    private final String name;

    private static final Logger LOGGER = Logger.getLogger(Transformer.class.getName());

    public Transformer(final String name, final Function<? super T, ? extends R> function){
        super();
        this.name = name;
        this.function = function;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item) {

        R transformedItem = (R) function.apply(item);
        LOGGER.info(String.format("%s transformed to %s by %s in %s thread",
                item, transformedItem, name, Thread.currentThread().getName()));
        submit(transformedItem);
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
