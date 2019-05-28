package com.example.mark.bean;

/**
 * 考试
 */
public class ExaminationBean {

    private String id;

    private double mark;

    private double avgMark;

    private double fullMark;

    public double getFullMark() {
        return fullMark;
    }

    public void setFullMark(double fullMark) {
        this.fullMark = fullMark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public double getAvgMark() {
        return avgMark;
    }

    public void setAvgMark(double avgMark) {
        this.avgMark = avgMark;
    }
}
