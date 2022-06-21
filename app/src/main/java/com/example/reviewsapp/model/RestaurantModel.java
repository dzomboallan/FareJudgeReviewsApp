package com.example.reviewsapp.model;

public class RestaurantModel {

    private int restaurantid;
    private byte [] restaurantavatar;
    private String restaurantname;

    public RestaurantModel(int restaurantid, byte[] restaurantavatar, String restaurantname) {
        this.restaurantid = restaurantid;
        this.restaurantavatar = restaurantavatar;
        this.restaurantname = restaurantname;
    }

    public int getRestaurantid() {
        return restaurantid;
    }

    public void setRestaurantid(int restaurantid) {
        this.restaurantid = restaurantid;
    }

    public byte[] getRestaurantavatar() {
        return restaurantavatar;
    }

    public void setRestaurantavatar(byte[] restaurantavatar) {
        this.restaurantavatar = restaurantavatar;
    }

    public String getRestaurantname() {
        return restaurantname;
    }

    public void setRestaurantname(String restaurantname) {
        this.restaurantname = restaurantname;
    }
}
