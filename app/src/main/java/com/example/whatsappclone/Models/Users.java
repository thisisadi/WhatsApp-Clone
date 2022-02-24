package com.example.whatsappclone.Models;

public class Users {
    String profilePic, userName, eMail, password,userID, lastMessage, about;

    public Users(String profilePic, String userName, String eMail, String password, String userID, String lastMessage, String about) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.eMail = eMail;
        this.password = password;
        this.userID = userID;
        this.lastMessage = lastMessage;
        this.about = about;
    }

    public Users(){}

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    // SignUp Constructor
    public Users(String userName, String eMail, String password) {
        this.userName = userName;
        this.eMail = eMail;
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
