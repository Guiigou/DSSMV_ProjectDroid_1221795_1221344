package com.example.libraryapplication.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Checkout {
    private boolean active;

    private LibraryBook book;

    private String createTimestamp;

    private String dueDate;

    private String id;

    private boolean overdue;

    private String updateTimestamp;

    private String userId;

    // Getters e Setters
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LibraryBook getBook() {
        return book;
    }

    public void setBook(LibraryBook book) {
        this.book = book;
    }

    public String getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(String createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(String updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
