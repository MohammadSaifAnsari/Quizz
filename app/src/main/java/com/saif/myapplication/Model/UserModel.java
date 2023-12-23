package com.saif.myapplication.Model;

public class UserModel {
    String userName,userMail,userID,userPassword,userPhoneNo;

    public UserModel(String userName, String userMail,  String userPassword,String userPhoneNo) {
        this.userName = userName;
        this.userMail = userMail;
        this.userPassword = userPassword;
        this.userPhoneNo = userPhoneNo;
    }
    public UserModel(String userName, String userMail) {
        this.userName = userName;
        this.userMail = userMail;
    }

    public UserModel(String userName, String userMail, String userPhoneNo) {
        this.userName = userName;
        this.userMail = userMail;
        this.userPhoneNo = userPhoneNo;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
