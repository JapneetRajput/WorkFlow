package com.example.workflow;

public class LeaveList {
    String Type;
    String to_date,from_date,email;
    Integer count;

    public LeaveList(){}

    public LeaveList(String Type, String to_date, String from_date, String email, Integer count) {
        this.Type = Type;
        this.to_date = to_date;
        this.from_date = from_date;
        this.email = email;
        this.count = count;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getType() {
        return Type;
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
