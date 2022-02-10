package com.example.workflow;

public class dataExtract {

    dataExtract(){}

    private String FirstName,LastName,Email,Username;

    dataExtract(String FirstName,String LastName,String Username,String Email){
        this.FirstName=FirstName;
        this.LastName=LastName;
        this.Email=Email;
        this.Username=Username;
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
