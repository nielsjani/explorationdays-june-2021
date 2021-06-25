package com.janistylez.springwebflux;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

public class CustomBaseSubscriber extends BaseSubscriber<Integer> {

    @Override
    protected void hookOnNext(Integer value) {
        System.out.println("Next " + value);;
    }

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        //Let users of this class decide when and how many items to fetch
    }
}
