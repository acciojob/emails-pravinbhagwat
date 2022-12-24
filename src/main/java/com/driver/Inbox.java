package com.driver;

import java.util.Date;

public class Inbox {
    Date date;
    String sender;
    String message;

    public Inbox(Date date, String sender, String message) {
        this.date = date;
        this.sender = sender;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
