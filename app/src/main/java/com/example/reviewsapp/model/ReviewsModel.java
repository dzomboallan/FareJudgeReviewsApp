package com.example.reviewsapp.model;

public class ReviewsModel {

    private int estid;
    private String estname;
    private String esttype;
    private String food;
    private String location;
    private String remarks;

    public ReviewsModel(int estid, String estname, String esttype, String food, String location, String remarks) {
        this.estid = estid;
        this.estname = estname;
        this.esttype = esttype;
        this.food = food;
        this.location = location;
        this.remarks = remarks;
    }

    public int getEstid() {
        return estid;
    }

    public void setEstid(int estid) {
        this.estid = estid;
    }

    public String getEstname() {
        return estname;
    }

    public void setEstname(String estname) {
        this.estname = estname;
    }

    public String getEsttype() {
        return esttype;
    }

    public void setEsttype(String esttype) {
        this.esttype = esttype;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


}
