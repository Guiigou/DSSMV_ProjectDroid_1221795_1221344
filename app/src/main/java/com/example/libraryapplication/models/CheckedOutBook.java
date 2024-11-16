package com.example.libraryapplication.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class CheckedOutBook{
    private boolean active;

    private String bookId;

    private String createTimestamp;

    private String dueDate;

    private String id;

    private String libraryAddress;

    private LocalTime libraryCloseTime;

    private String libraryId;

    private String libraryName;

    private LocalTime libraryOpenTime;

    private String updateTimestamp;

    private String userId;

    private Book book;

    // Getters e Setters
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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

    public String getLibraryAddress() {
        return libraryAddress;
    }

    public void setLibraryAddress(String libraryAddress) {
        this.libraryAddress = libraryAddress;
    }

    public LocalTime getLibraryCloseTime() {
        return libraryCloseTime;
    }

    public void setLibraryCloseTime(LocalTime libraryCloseTime) {
        this.libraryCloseTime = libraryCloseTime;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public LocalTime getLibraryOpenTime() {
        return libraryOpenTime;
    }

    public void setLibraryOpenTime(LocalTime libraryOpenTime) {
        this.libraryOpenTime = libraryOpenTime;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
