package com.saif.myapplication.Model;

public class CategoryModel {
    private String name;
    private  String id;
    private String noOfTest;

    public CategoryModel(String name,  String noOfTest) {
        this.name = name;
        this.noOfTest = noOfTest;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoOfTest() {
        return noOfTest;
    }

    public void setNoOfTest(String noOfTest) {
        this.noOfTest = noOfTest;
    }
}
