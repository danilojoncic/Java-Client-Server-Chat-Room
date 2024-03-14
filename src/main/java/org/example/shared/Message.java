package org.example.shared;

import java.util.Date;

public class Message implements Comparable<Message>{
    private String author;
    private String content;
    private Date sentTime;

    public Message(String author, String content, Date sentTime) {
        this.author = author;
        this.content = content;
        this.sentTime = sentTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    @Override
    public String toString() {
        return "[" + sentTime.toString() + "]" + author + "->" + content;
    }

    @Override
    public int compareTo(Message o) {
        return this.sentTime.after(o.sentTime)? 1: 0;
    }
}
