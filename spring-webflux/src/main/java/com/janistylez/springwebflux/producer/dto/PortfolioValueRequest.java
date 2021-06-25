package com.janistylez.springwebflux.producer.dto;

import java.io.Serializable;
import java.util.List;

public class PortfolioValueRequest implements Serializable {

    private List<Integer> ids;

    public PortfolioValueRequest() {
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
