package com.example.libraryapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookDTO {
    @SerializedName("title")
    private String title;
    @SerializedName("authors")
    private List<String> authors;
   // private String coverUrl; // Adicionado para incluir a capa do livro

    // Construtor
    public BookDTO(String title, List<String> authors) {
        this.title = title;
        this.authors = authors;
        //this.coverUrl = coverUrl;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

  /*  public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }*/
}
