package com.janistylez.springwebflux.consumer;

import com.janistylez.springwebflux.producer.dto.StockInfo;
import com.janistylez.springwebflux.producer.dto.StockNonFinancialInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;

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

        stockInfoMono.subscribe(this::handleUsingLog);
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
