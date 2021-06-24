package com.janistylez.springwebflux.producer.dto;

import java.io.Serializable;

public class PortfolioItem implements Serializable {

    private int id;
    private int stockId;
    private int amount;

    public PortfolioItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
