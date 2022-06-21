package com.example.reviewsapp.model;

public class BarModel {

    private int id;
    private byte [] proavatar;
    private String barname;

    public BarModel(int id, byte[] proavatar, String barname) {
        this.id = id;
        this.proavatar = proavatar;
        this.barname = barname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getProavatar() {
        return proavatar;
    }

    public void setProavatar(byte[] proavatar) {
        this.proavatar = proavatar;
    }

    public String getBarname() {
        return barname;
    }

    public void setBarname(String barname) {
        this.barname = barname;
    }
}
