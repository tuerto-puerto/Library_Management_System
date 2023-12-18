package com.example.library_system;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Book {

    private int id;
    private String title;
    private String description;
    private String author;
    private double price;
    private LocalDate year; // Updated to LocalDate

    public Book() {}

    public Book(int id, String title, String author, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public long getEspn() {
        return espn;
    }

    public void setEspn(long espn) {
        this.espn = espn;
    }

    private long espn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public void setYearFromUtilDate(Date utilDate) {
        this.year = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
