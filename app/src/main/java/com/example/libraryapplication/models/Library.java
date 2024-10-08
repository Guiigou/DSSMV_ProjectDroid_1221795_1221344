package com.example.libraryapplication.models;

public class Library {
    private String id;
    private String name;
    private String address;
    private LocalTime openTime;
    private LocalTime closeTime;
    private boolean open;
    private String openDays;
    private String openStatement;

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalTime getOpenTime() { return openTime; }
    public void setOpenTime(LocalTime openTime) { this.openTime = openTime; }

    public LocalTime getCloseTime() { return closeTime; }
    public void setCloseTime(LocalTime closeTime) { this.closeTime = closeTime; }

    public boolean isOpen() { return open; }
    public void setOpen(boolean open) { this.open = open; }

    public String getOpenDays() { return openDays; }
    public void setOpenDays(String openDays) { this.openDays = openDays; }

    public String getOpenStatement() { return openStatement; }
    public void setOpenStatement(String openStatement) { this.openStatement = openStatement; }
}
