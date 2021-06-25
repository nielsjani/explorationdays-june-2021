package com.janistylez.springwebflux.producer.rest;

import com.janistylez.springwebflux.producer.dto.PortfolioItem;
import com.janistylez.springwebflux.producer.dto.StockInfo;
import com.janistylez.springwebflux.producer.dto.StockNonFinancialInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/stonks")
@CrossOrigin(origins = "*")
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
    //Dont forget the mediatype or the consumer wont receive anything! Has to be a json stream for Angular to pick it up.
    @GetMapping(value = "/{id}/stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<StockInfo> getStocks(@PathVariable int id, @RequestParam int delay) {
        //Generate a new result every <delay> seconds
        return Flux.interval(Duration.ofSeconds(delay))
                .map(d -> stockRepo.getLatest(id))
                .onErrorResume(Mono::error)
                // End stream after 1 minute. Otherwise risk stream would run infinitely
                .take(Duration.ofMinutes(1));

    }

    @GetMapping(value = "/portfolio/{portfolioId}")
    public Mono<List<PortfolioItem>> getPortfolio(@PathVariable int portfolioId) {
        //Seems like there is no way to handle regular thrown exception gracefully using a chainable method
        // Handling Mono.error is easy, though
        return stockRepo.getPortfolio(portfolioId)
                .onErrorReturn(List.of());
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
