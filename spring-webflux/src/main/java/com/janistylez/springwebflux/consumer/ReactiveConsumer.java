package com.janistylez.springwebflux.consumer;

import com.janistylez.springwebflux.producer.dto.PortfolioValueRequest;
import com.janistylez.springwebflux.producer.dto.StockInfo;
import com.janistylez.springwebflux.producer.dto.StockNonFinancialInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;

import static java.util.Arrays.asList;

@RestController()
@RequestMapping("/consumer")
public class ReactiveConsumer {

    private static final String BASEURL = "http://localhost:8080";

    @GetMapping("/mono")
    public void consumeMono() {
        Mono<StockInfo> stockInfoMono = webClient()
                .get()
                .uri("/stonks/{id}", 42)
                .retrieve()
                .bodyToMono(StockInfo.class);

        //Due to the delay built in the Controller, the subscribe's code will be executed after the sout at the end of this method
        stockInfoMono.subscribe(this::handleUsingLog);
        System.out.println("Aaaand we're done");
    }

    @GetMapping("/monoCollection")
    public void consumeMonoCollection() {
        Mono<List<StockNonFinancialInfo>> stockInfoMono = webClient()
                .get()
                .uri("/stonks")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});

        stockInfoMono.subscribe(this::handleListUsingLog);
    }

    @GetMapping("/flux")
    public void consumeFlux() {
        Flux<StockInfo> stockInfoFlux = webClient()
                .get()
                .uri("/stonks/{id}/stream?delay=2", 1)
                .retrieve()
                .bodyToFlux(StockInfo.class);

        Disposable subscribe = stockInfoFlux
                .doOnComplete(() -> System.out.println("Stream completed"))
                .subscribe(this::handleUsingLog);
    }

    @GetMapping("/flux/zip")
    public void consumeFluxZip() {
        PortfolioValueRequest portfolioValueRequest = new PortfolioValueRequest();
        portfolioValueRequest.setIds(asList(1,2,3));
        Flux<List<StockInfo>> stockInfoFlux = webClient()
                .post()
                .uri("/stonks/portfolio/value")
                .body(Mono.just(portfolioValueRequest), PortfolioValueRequest.class)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<>() {});

        Disposable subscribe = stockInfoFlux
                .doOnComplete(() -> System.out.println("Stream completed"))
                .subscribe(this::handleListUsingLog);
    }

    private void handleUsingLog(Serializable result) {
        System.out.println(result);
    }

    private void handleListUsingLog(List<? extends Serializable> result) {
        System.out.println(result);
    }

    private WebClient webClient() {
        return WebClient.create(BASEURL);
    }
}
