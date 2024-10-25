package com.example.libraryapplication.models;

import com.google.gson.annotations.SerializedName;

public class Library {
    @SerializedName("id")
    private String id;  // UUID

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("open")
    private boolean open;

    @SerializedName("openDays")
    private String openDays;

    @SerializedName("openTime")  // Este campo agora será uma String
    private String openTime;

    @SerializedName("closeTime")  // Este campo agora será uma String
    private String closeTime;

    // Getters e setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getOpenDays() {
        return openDays;
    }

    public void setOpenDays(String openDays) {
        this.openDays = openDays;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }
}
