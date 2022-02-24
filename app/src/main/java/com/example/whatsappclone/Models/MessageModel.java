package com.example.whatsappclone.Models;

public class MessageModel {
    String uID, message, messageID;
    Long timestamp;

    public MessageModel(){}

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public MessageModel(String uID, String message,String messageID, Long timestamp) {
        this.uID = uID;
        this.message = message;
        this.messageID = messageID;
        this.timestamp = timestamp;
    }

    public MessageModel(String uID, String message) {
        this.uID = uID;
        this.message = message;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
