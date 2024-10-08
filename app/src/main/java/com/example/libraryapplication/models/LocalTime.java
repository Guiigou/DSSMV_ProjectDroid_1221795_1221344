package com.example.libraryapplication.models;

public class LocalTime {
    private int hour;
    private int minute;
    private int second;
    private int nano;

    // Getters e Setters
    public int getHour() { return hour; }
    public void setHour(int hour) { this.hour = hour; }

    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }

    public int getSecond() { return second; }
    public void setSecond(int second) { this.second = second; }

    public int getNano() { return nano; }
    public void setNano(int nano) { this.nano = nano; }
}
