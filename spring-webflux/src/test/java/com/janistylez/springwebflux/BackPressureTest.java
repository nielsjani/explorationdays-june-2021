package com.janistylez.springwebflux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

//https://www.baeldung.com/spring-webflux-backpressure
public class BackPressureTest {

    @Test
    public void requestingInChunks() {
        BaseSubscriber<Integer> baseSubscriber = new CustomBaseSubscriber() {
        };
        Flux.range(1, 10)
                .subscribe(baseSubscriber);

        System.out.println("Requesting 2 items");
        baseSubscriber.request(2);
        System.out.println("Requesting 3 Items");
        baseSubscriber.request(3);
        System.out.println("Requesting 4 Items");
        baseSubscriber.request(4);
        System.out.println("Requesting 999 Items");
        baseSubscriber.request(999);
    }

    @Test
    public void publisherLimitsRate() {

        //limitRate refills to 75% of total
        //First request(8)
        //Refill to 6
        //Second request(6)
        //Refill to 6
        //Third request(6), but will only receive 1 since there are only 15 elements
        Flux.range(1, 15)
                //Log isnt needed for ratelimiting to work, but helps you understand what is happening under the hood
                .log()
                .limitRate(8)
                .subscribe(
                        System.out::println,
                        Throwable::printStackTrace,
                        () -> System.out.println("Finished!!"),
                        subscription -> subscription.request(15)
                );
    }
}
