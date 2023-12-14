package com.saif.myapplication.Model;

public class CategoryModel {
    private String CAT_ID;
    private String NAME;
    private String NO_OF_TESTS;

    public CategoryModel(String CAT_ID, String NAME, String NO_OF_TESTS) {
        this.CAT_ID = CAT_ID;
        this.NAME = NAME;
        this.NO_OF_TESTS = NO_OF_TESTS;
    }

    public String getCAT_ID() {
        return CAT_ID;
    }

    public void setCAT_ID(String CAT_ID) {
        this.CAT_ID = CAT_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getNO_OF_TEST() {
        return NO_OF_TESTS;
    }

    public void setNO_OF_TEST(String NO_OF_TEST) {
        this.NO_OF_TESTS = NO_OF_TEST;
    }
}