package com.janistylez.springwebflux.producer.rest;

import java.util.Random;

public class StockRepoEntry {
    private String name;
    private float startValue;
    private int fluctationRate;
    private Float lastCalculatedValue;

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

    public float getMostRecentValue() {
        if(lastCalculatedValue == null) {
            lastCalculatedValue = startValue;
            return startValue;
        } else {
            int percentage = determineChangePercentage();
            System.out.println(percentage + "%");
            lastCalculatedValue = (lastCalculatedValue * (100 + percentage))/100;
            return lastCalculatedValue;
        }
    }

    private int determineChangePercentage() {
        Random r = new Random();
        int low = fluctationRate*-1;
        int high = fluctationRate;
        return r.nextInt(high-low) + low;
    }
}
