package com.example.libraryapplication.models;

import java.util.List;

public class Book {
    private String title;
    private String description;
    private String isbn;
    private String publishDate;
    private int numberOfPages;
    private CoverUrls cover;
    private List<String> authors; // Pode ser uma lista de autores
    private String byStatement;
    private List<String> subjectPeople;
    private List<String> subjectPlaces;
    private List<String> subjectTimes;
    private List<String> subjects;

    // Construtor para criar um novo livro
    public Book(String title, String description, String isbn, String publishDate, int numberOfPages, CoverUrls cover, List<String> authors) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.numberOfPages = numberOfPages;
        this.cover = cover;
        this.authors = authors;
    }

    // Getters e Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public CoverUrls getCover() {
        return cover;
    }

    public void setCover(CoverUrls cover) {
        this.cover = cover;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getByStatement() {
        return byStatement;
    }

    public void setByStatement(String byStatement) {
        this.byStatement = byStatement;
    }

    public List<String> getSubjectPeople() {
        return subjectPeople;
    }

    public void setSubjectPeople(List<String> subjectPeople) {
        this.subjectPeople = subjectPeople;
    }

    public List<String> getSubjectPlaces() {
        return subjectPlaces;
    }

    public void setSubjectPlaces(List<String> subjectPlaces) {
        this.subjectPlaces = subjectPlaces;
    }

    public List<String> getSubjectTimes() {
        return subjectTimes;
    }

    public void setSubjectTimes(List<String> subjectTimes) {
        this.subjectTimes = subjectTimes;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }
}
