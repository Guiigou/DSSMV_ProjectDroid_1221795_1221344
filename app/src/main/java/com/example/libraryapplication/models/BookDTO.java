package com.example.libraryapplication.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BookDTO {
    @SerializedName("book")
    private Book book; // Importa a classe Book diretamente do package models

    // Getters e setters para o objeto `Book`
    public String getTitle() {
        return book != null ? book.getTitle() : null;
    }

    public List<Author> getAuthors() {
        return book != null ? book.getAuthors() : null;
    }

    public String getCoverUrl() {
        return book != null && book.getCover() != null ? book.getCover().getSmallUrl() : null;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getIsbn() {
        return book != null ? book.getIsbn() : null;

    }

}
