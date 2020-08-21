package main;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private int reqSuccess;
    private int reqFail;
    private int time;
    private double st;
    private double sst;
    private double min;
    private double max;
    private final List<Double> timeRequests;

    public Report() {
        timeRequests = new ArrayList<>();
        this.max = 0;
        this.min = Double.MAX_VALUE;
    }

    public int getReqSuccess() {
        return reqSuccess;
    }

    public void setReqSuccess(int reqSuccess) {
        this.reqSuccess = reqSuccess;
    }

    public int getReqFail() {
        return reqFail;
    }

    public void setReqFail(int reqFail) {
        this.reqFail = reqFail;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getSt() {
        return st;
    }

    public void setSt(double st) {
        this.st = st;
    }

    public double getSst() {
        return sst;
    }

    public void setSst(double sst) {
        this.sst = sst;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void addResult(double timeRequest) {
        timeRequests.add(timeRequest);
    }

    public List<Double> getTimeRequests() {
        return timeRequests;
    }
}
