package com.five;

public class Book {
    private String title;
    private String isbn;
    private String author;
    private Integer totalAmount;
    private Integer currentAmount;

    public Book(String title, String isbn, String author, Integer totalAmount, Integer currentAmount) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.totalAmount = totalAmount;
        this.currentAmount = currentAmount;
    }

    public Book(String title, String isbn, String author, Integer totalAmount) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.totalAmount = totalAmount;
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

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Integer currentAmount) {
        this.currentAmount = currentAmount;
    }
}
