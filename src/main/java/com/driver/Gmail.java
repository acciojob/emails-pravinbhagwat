package com.driver;

import java.util.*;

public class Gmail extends Email {

    int inboxCapacity; //maximum number of mails inbox can store
    //Inbox: Stores mails. Each mail has date (Date), sender (String), message (String). It is guaranteed that message is distinct for all mails.
    //Trash: Stores mails. Each mail has date (Date), sender (String), message (String)

    private int iCount;
    TreeMap<Integer, Inbox> inbox;
    TreeMap<Integer, Trash> trash;
    private int tCount;

    public Gmail(String emailId, int inboxCapacity) {
        super(emailId);
        this.inboxCapacity = inboxCapacity;
        this.iCount = 0;
        this.inbox = new TreeMap<>();
        this.trash = new TreeMap<>();
    }

    public void receiveMail(Date date, String sender, String message){
        // If the inbox is full, move the oldest mail in the inbox to trash and add the new mail to inbox.
        // It is guaranteed that:
        // 1. Each mail in the inbox is distinct.
        // 2. The mails are received in non-decreasing order. This means that the date of a new mail is greater than equal to the dates of mails received already.

        if(inbox.size() == this.inboxCapacity) {
            Inbox tMail = inbox.firstEntry().getValue();
            trash.put(++tCount, new Trash(tMail.getDate(), tMail.getSender(), tMail.getMessage()));
        }
        Inbox newEmail = new Inbox(date, sender, message);
        inbox.put(++iCount, newEmail);
    }

    public void deleteMail(String message){
        // Each message is distinct
        // If the given message is found in any mail in the inbox, move the mail to trash, else do nothing

        if(inbox.size() == 0) return;
        for(Map.Entry<Integer, Inbox> mail : inbox.entrySet()) {
            Inbox iMail = mail.getValue();
            if(iMail.getMessage().equals(message)) {
                Trash tMail = new Trash(iMail.getDate(), iMail.sender, iMail.getMessage());
                trash.put(++this.tCount, tMail);
                inbox.remove(mail.getKey());
                this.iCount--;
                return;
            }
        }
    }

    public String findLatestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the latest mail present in the inbox

        if(this.inbox.size() == 0) return null;
        Inbox mail = inbox.lastEntry().getValue();
        return mail.getMessage();
    }

    public String findOldestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the oldest mail present in the inbox

        if(this.inbox.size() == 0) return null;
        Inbox mail = inbox.firstEntry().getValue();
        return mail.getMessage();
    }

    public int findMailsBetweenDates(Date start, Date end){
        //find number of mails in the inbox which are received between given dates
        //It is guaranteed that start date <= end date

        int mailsCount = 0;

        for(Inbox iMails : inbox.values()) {
            if((iMails.getDate().compareTo(start) > 0 && iMails.getDate().compareTo(end) < 0)
                    || (iMails.getDate().equals(start) || iMails.getDate().equals(end))) {
                mailsCount++;
            }
        }
        return mailsCount;
    }

    public int getInboxSize(){
        // Return number of mails in inbox
        return inbox.size();
    }

    public int getTrashSize(){
        // Return number of mails in Trash
        return trash.size();
    }

    public void emptyTrash(){
        // clear all mails in the trash
        trash.clear();
        tCount = 0;
    }

    public int getInboxCapacity() {
        // Return the maximum number of mails that can be stored in the inbox
        return this.inboxCapacity;
    }


}
