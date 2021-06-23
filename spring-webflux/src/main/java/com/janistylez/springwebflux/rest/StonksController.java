package com.janistylez.springwebflux.rest;

import com.janistylez.springwebflux.dto.StockInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/stonks")
public class StonksController {

    @GetMapping("/{id}")
    public Mono<StockInfo> getLatestStockInfo(@PathVariable int id) {
        StockInfo data = new StockInfo();
        data.setId(id);
        data.setName("Somestock");
        data.setTime(LocalDateTime.now());
        data.setValue(1.234f);
        return Mono.just(data);
    }
}
