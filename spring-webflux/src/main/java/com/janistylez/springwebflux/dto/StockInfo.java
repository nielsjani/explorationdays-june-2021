package com.janistylez.springwebflux.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class StockInfo implements Serializable {
    private int id;
    private String name;
    private float value;
    private LocalDateTime time;

    public StockInfo(){}

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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
