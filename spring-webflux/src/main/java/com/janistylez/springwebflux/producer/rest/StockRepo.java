package com.janistylez.springwebflux.producer.rest;

import com.janistylez.springwebflux.producer.dto.PortfolioItem;
import com.janistylez.springwebflux.producer.dto.StockInfo;
import com.janistylez.springwebflux.producer.dto.StockNonFinancialInfo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

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

    public StockInfo getLatest(int id) {
        if (!stocks.containsKey(id)) {
            throw new IllegalArgumentException("Stock not found");
        }
        StockRepoEntry stockRepoEntry = stocks.get(id);

        StockInfo result = new StockInfo();
        result.setId(id);
        result.setName(stockRepoEntry.getName());
        result.setValue(stockRepoEntry.getMostRecentValue());
        result.setTime(LocalDateTime.now());
        return result;
    }

    public Mono<List<PortfolioItem>> getPortfolio(int portfolioId) {
        if (portfolioId != 0) {
            return Mono.error(new IllegalArgumentException("Unknown portfolio"));
        }
        return Mono.just(asList(aPortfolioItem(1, 1, 20),
                aPortfolioItem(2, 2, 100),
                aPortfolioItem(3, 3, 5)));
    }

    private PortfolioItem aPortfolioItem(int id, int stockId, int amount) {
        PortfolioItem portfolioItem = new PortfolioItem();
        portfolioItem.setId(id);
        portfolioItem.setStockId(stockId);
        portfolioItem.setAmount(amount);
        return portfolioItem;
    }
}
