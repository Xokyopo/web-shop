package ru.geekbrains.coursework.webshop.app.domain.entities;

public class ChatMessage {
    private String author;
    private String message;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "author='" + author + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
