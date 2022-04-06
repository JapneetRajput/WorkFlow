package com.example.workflow;

public class dataExtract {

    dataExtract(){}

    private String FirstName;
    private String LastName;
    private String Email;
    private String Department;

    private String Position;


    dataExtract(String FirstName, String LastName, String Department, String Email, String Position){
        this.FirstName=FirstName;
        this.LastName=LastName;
        this.Email=Email;
        this.Department=Department;
        this.Position=Position;
    }

    public String getPosition() {
        return Position;
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

    public String getDepartment() {
        return Department;
    }
}
