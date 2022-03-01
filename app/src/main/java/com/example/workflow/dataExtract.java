package com.example.workflow;

public class dataExtract {

    dataExtract(){}


    private String FirstName,LastName,Email,Username,Position;

    dataExtract(String FirstName,String LastName,String Username,String Email, String Position){
        this.FirstName=FirstName;
        this.LastName=LastName;
        this.Email=Email;
        this.Username=Username;
        this.Position=Position;
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

    public String getUsername() { return Username; }

    public String getPosition() {
        return Position;
    }
}
