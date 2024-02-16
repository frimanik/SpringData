package com.example.SpringData_JDBC.Entities;

import java.time.Year;
import jakarta.persistence.*;

@Entity
 public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String title;
    String author;
    Year publicationDate;

    public Book(long id, String title, String author, Year publicationDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Year getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Year publicationDate) {
        this.publicationDate = publicationDate;
    }
}
