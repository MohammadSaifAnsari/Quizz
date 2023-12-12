package com.saif.myapplication.Model;

public class TestModel {
   private String testID;
   private int maxScore,time;

    public TestModel(String testID, int maxScore, int time) {
        this.testID = testID;
        this.maxScore = maxScore;
        this.time = time;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
