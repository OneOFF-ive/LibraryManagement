package com.five;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Book {
    private String title;
    private String isbn;
    private String author;
    private int totalAmount;
    private int currentAmount;

    public Book(String title, String isbn, String author, int totalAmount, int currentAmount) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.totalAmount = totalAmount;
        this.currentAmount = currentAmount;
    }

    public Book(String title, String isbn, String author, int totalAmount) {
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

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    @JsonIgnore
    public boolean isRented() {
        return currentAmount != totalAmount;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", totalAmount=" + totalAmount +
                ", currentAmount=" + currentAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book data = (Book) o;
        return isbn.equals(data.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
