package com.five;

public class Book {
    private String title;
    private String isbn;
    private String author;
    private Integer amount;

    public Book(String title, String isbn, String author, Integer amount) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.amount = amount;
    }

    public Book() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
