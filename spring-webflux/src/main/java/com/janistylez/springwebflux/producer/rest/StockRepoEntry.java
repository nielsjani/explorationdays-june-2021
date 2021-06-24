package com.janistylez.springwebflux.producer.rest;

public class StockRepoEntry {
    private String name;
    private float startValue;
    private int fluctationRate;

    public StockRepoEntry(String name, float startValue, int fluctationRate) {
        this.name = name;
        this.startValue = startValue;
        this.fluctationRate = fluctationRate;
    }

    public String getName() {
        return name;
    }

    public float getStartValue() {
        return startValue;
    }

    public int getFluctationRate() {
        return fluctationRate;
    }
}
