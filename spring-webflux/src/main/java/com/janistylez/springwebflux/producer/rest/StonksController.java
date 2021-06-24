package com.janistylez.springwebflux.producer.rest;

import com.janistylez.springwebflux.producer.dto.StockInfo;
import com.janistylez.springwebflux.producer.dto.StockNonFinancialInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

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

    private StockInfo randomStock(int id) {
        StockInfo data = new StockInfo();
        data.setId(id);
        data.setName("Somestock");
        data.setTime(LocalDateTime.now());
        data.setValue(1.234f);
        return data;
    }
}
