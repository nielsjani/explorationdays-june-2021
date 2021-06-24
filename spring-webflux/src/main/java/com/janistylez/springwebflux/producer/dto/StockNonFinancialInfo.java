package com.janistylez.springwebflux.producer.dto;

import java.io.Serializable;

public class StockNonFinancialInfo implements Serializable {
    private int id;
    private String name;


    public StockNonFinancialInfo(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StockNonFinancialInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
