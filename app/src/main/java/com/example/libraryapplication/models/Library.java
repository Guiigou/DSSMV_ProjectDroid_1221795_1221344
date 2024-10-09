package com.example.libraryapplication.models;

public class Library {
    private String id;
    private String name;
    private String address;
    private boolean open;
    private String openDays;
    private String openStatement;
    private String openTime;  // Atualizado para String
    private String closeTime;  // Atualizado para String

    // Getters e setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public boolean isOpen() { return open; }
    public void setOpen(boolean open) { this.open = open; }

    public String getOpenDays() { return openDays; }
    public void setOpenDays(String openDays) { this.openDays = openDays; }

    public String getOpenStatement() { return openStatement; }
    public void setOpenStatement(String openStatement) { this.openStatement = openStatement; }

    public String getOpenTime() { return openTime; }
    public void setOpenTime(String openTime) { this.openTime = openTime; }

    public String getCloseTime() { return closeTime; }
    public void setCloseTime(String closeTime) { this.closeTime = closeTime; }
}
