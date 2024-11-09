package com.example.libraryapplication.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LibraryBook implements Serializable {
    @SerializedName("available")
    private int available;

    @SerializedName("checkedOut")
    private int checkedOut;

    @SerializedName("stock")
    private int stock;

    @SerializedName("isbn")
    private String isbn;

    @SerializedName("book")
    private Book book;

    @SerializedName("library")
    private Library library;

    // Construtor para inicializar o LibraryBook
    public LibraryBook(int available, int checkedOut, int stock, String isbn, Book book, Library library) {
        this.available = available;
        this.checkedOut = checkedOut;
        this.stock = stock;
        this.isbn = isbn;
        this.book = book;
        this.library = library;
    }

    // Getters e Setters
    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(int checkedOut) {
        this.checkedOut = checkedOut;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    // Métodos utilitários para acessar informações do Book e Library
    public String getBookTitle() {
        return book != null ? book.getTitle() : "Título indisponível";
    }

    public String getBookDescription() {
        return book != null ? book.getDescription() : "Descrição indisponível";
    }

    public String getBookAuthors() {
        if (book != null && book.getAuthors() != null) {
            StringBuilder authorsText = new StringBuilder();
            for (Author author : book.getAuthors()) {
                if (authorsText.length() > 0) {
                    authorsText.append(", ");
                }
                authorsText.append(author.getName());
            }
            return authorsText.toString();
        }
        return "Autor desconhecido";
    }

    public String getLibraryName() {
        return library != null ? library.getName() : "Biblioteca desconhecida";
    }

    public String getLibraryAddress() {
        return library != null ? library.getAddress() : "Endereço desconhecido";
    }
}
