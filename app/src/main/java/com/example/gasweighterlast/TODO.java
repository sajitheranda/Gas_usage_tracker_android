package com.example.gasweighterlast;

public class TODO {
    private int id;
    private String title,number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public TODO(int id, String title, String number) {
        this.id = id;
        this.title = title;
        this.number = number;

    }

    public TODO( String title, String number) {
        this.title = title;
        this.number = number;

    }

    public TODO(){

    }
}
