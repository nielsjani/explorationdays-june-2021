package com.janistylez.springwebflux.producer.rest;

import com.janistylez.springwebflux.producer.dto.StockNonFinancialInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StockRepo {

    private Map<Integer, StockRepoEntry> stocks;

    public StockRepo() {
        this.stocks = Map.of(
                1, new StockRepoEntry("WayneCorp", 1500, 2),
                2, new StockRepoEntry("Stark Enterprises", 200, 10),
                3, new StockRepoEntry("SW Mining Co", 900, 1)
        );
    }

    public List<StockNonFinancialInfo> getAll() {
        return stocks.keySet().stream()
                .map(id -> {
                    StockNonFinancialInfo res = new StockNonFinancialInfo();
                    res.setId(id);
                    res.setName(stocks.get(id).getName());
                    return res;
                })
                .collect(Collectors.toList());
    }
}
