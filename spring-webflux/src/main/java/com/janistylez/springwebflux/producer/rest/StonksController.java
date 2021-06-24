package com.janistylez.springwebflux.producer.rest;

import com.janistylez.springwebflux.producer.dto.StockInfo;
import com.janistylez.springwebflux.producer.dto.StockNonFinancialInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/stonks")
public class StonksController {

    @Autowired
    private StockRepo stockRepo;

    @GetMapping("/{id}")
    public Mono<StockInfo> getLatestStockInfo(@PathVariable int id) {
        return Mono.just(randomStock(id));
    }

    @GetMapping
    public Mono<List<StockNonFinancialInfo>> getStockOverview() {
        return Mono.just(stockRepo.getAll());
    }

    //https://medium.com/@fede.lopez/take-reactive-programming-with-spring-to-the-infinity-and-beyond-965a4c15b26
    @GetMapping(value="/{id}/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StockInfo> getStocks(@PathVariable int id, @RequestParam int delay) {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(delay));
        Flux<StockInfo> stockInfoFlux = Flux.fromStream(Stream.generate(() -> stockRepo.getLatest(id)));

        //Combine fluxes so we generate a new result every <delay> seconds
        return Flux.zip(interval, stockInfoFlux).map(Tuple2::getT2)
                // End stream after 1 minute. Otherwise risk stream would run infinitely
                .take(Duration.ofMinutes(1));
    }

    private StockInfo randomStock(int id) {
        StockInfo data = new StockInfo();
        data.setId(id);
        data.setName("Somestock");
        data.setTime(LocalDateTime.now());
        data.setValue(1.234f);
        return data;
    }
}
