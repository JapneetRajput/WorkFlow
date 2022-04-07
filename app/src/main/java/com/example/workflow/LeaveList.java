package com.example.workflow;

public class LeaveList {
    String Type;
    String department;
    String description,to_date,from_date,email;

    public LeaveList(String Type, String department, String description, String to_date, String from_date, String email) {
        this.Type = Type;
        this.department = department;
        this.description = description;
        this.to_date = to_date;
        this.from_date = from_date;
        this.email = email;
    }

    public String getType() {
        return Type;
    }

    public String getDepartment() {
        return department;
    }

    public String getDescription() {
        return description;
    }

    public String getTo_date() {
        return to_date;
    }

    public String getFrom_date() {
        return from_date;
    }

    public String getEmail() {
        return email;
    }
}
