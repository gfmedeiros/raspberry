package org.example.raspberry.controllers;

import org.example.raspberry.services.ConsecutiveWins;

import java.util.List;

public class Result {
    private List<ConsecutiveWins> min;
    private List<ConsecutiveWins> max;

    public List<ConsecutiveWins> getMin() {
        return min;
    }

    public void setMin(List<ConsecutiveWins> min) {
        this.min = min;
    }

    public List<ConsecutiveWins> getMax() {
        return max;
    }

    public void setMax(List<ConsecutiveWins> max) {
        this.max = max;
    }
}
