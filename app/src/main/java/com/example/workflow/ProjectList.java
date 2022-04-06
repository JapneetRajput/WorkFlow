package com.example.workflow;

public class ProjectList {

    String title;
    String description;


    String department;


    Integer count;

    public ProjectList(){}

    public ProjectList(String title, String description,Integer count,String department) {
        this.title = title;
        this.description = description;
        this.count=count;
        this.department=department;
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
