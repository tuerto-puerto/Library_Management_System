package com.example.library_system;

public class UserInfo {
    private static UserInfo instance; // Singleton pattern
    private String userName;
    private int userId;

    private UserInfo() {
        // Private constructor to enforce singleton pattern
    }
    public static UserInfo getInstance() {
        if (instance == null) {
            instance = new UserInfo();
        }
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
