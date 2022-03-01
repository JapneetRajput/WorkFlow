package com.example.workflow;

public class dataExtract {

    dataExtract(){}

    private String FirstName,LastName,Email,Username;
    private Integer starCount;

    public Integer getStarCount() {
        return starCount;
    }

    dataExtract(String FirstName, String LastName, String Username, String Email, Integer starCount){
        this.FirstName=FirstName;
        this.LastName=LastName;
        this.Email=Email;
        this.Username=Username;
        this.starCount= starCount;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getUsername() {
        return Username;
    }
}
