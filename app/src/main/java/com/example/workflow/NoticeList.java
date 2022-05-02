package com.example.workflow;

public class NoticeList {

    String title;
    String description;


    String department;


    Integer count;

    public NoticeList(){}

    public NoticeList(String title, String description,Integer count,String department) {
        this.title = title;
        this.description = description;
        this.count=count;
        this.department=department;
    }

    public void changeText1(String text){

    }

    public String getTitle() {
        return title;
    }

    public String getDepartment() {
        return department;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
