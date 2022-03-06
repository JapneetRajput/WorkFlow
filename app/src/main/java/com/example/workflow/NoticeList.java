package com.example.workflow;

public class NoticeList {

    String title,description;


    Integer count;

    public NoticeList(){}

    public NoticeList(String title, String description,Integer count) {
        this.title = title;
        this.description = description;
        this.count=count;
    }

    public String getTitle() {
        return title;
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
