package com.example.library_system;

import java.util.Date;

public class Reservation {
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public java.sql.Date getEnding_date() {
        return (java.sql.Date) ending_date;
    }

    public void setEnding_date(Date ending_date) {
        this.ending_date = ending_date;
    }

    private int user_id;
    private int book_id;
    private Date ending_date;
}
