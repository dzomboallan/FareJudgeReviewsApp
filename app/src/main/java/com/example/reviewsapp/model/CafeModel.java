package com.example.reviewsapp.model;

public class CafeModel {

    private int cafeid;
    private byte [] cafeavatar;
    private String cafename;

    public CafeModel(int cafeid, byte[] cafeavatar, String cafename) {
        this.cafeid = cafeid;
        this.cafeavatar = cafeavatar;
        this.cafename = cafename;
    }

    public int getCafeid() {
        return cafeid;
    }

    public void setCafeid(int cafeid) {
        this.cafeid = cafeid;
    }

    public byte[] getCafeavatar() {
        return cafeavatar;
    }

    public void setCafeavatar(byte[] cafeavatar) {
        this.cafeavatar = cafeavatar;
    }

    public String getCafename() {
        return cafename;
    }

    public void setCafename(String cafename) {
        this.cafename = cafename;
    }
}
