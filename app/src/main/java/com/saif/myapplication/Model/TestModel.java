package com.saif.myapplication.Model;

public class TestModel {
   private String testID,time;
   private int maxScore;

    public TestModel(String testID, int maxScore, String time) {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
