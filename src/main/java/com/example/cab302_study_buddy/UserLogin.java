package com.example.cab302_study_buddy;


public class UserLogin {

    private int id;
    private String username;
    private String password;
    private String identifier;

    // Constructor
    public UserLogin(int id, String username, String password, String identifier)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.identifier = identifier;
    }

    // Getters and setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "username=" + username +
                ", password='" + password + '\'' +
                '}';
    }
}
