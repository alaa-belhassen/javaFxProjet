package tn.esprit.javafxproject.models;


import java.sql.Timestamp;

public class Publication {

    private int publicationID;
    private int idUser;
    private String content;
    private Timestamp timestamp;
    private int likes;
    private int shares;
    private String attachments;

    // Constructors, getters, and setters

    public Publication() {
    }

    public Publication(int idUser, String content, int likes, int shares, String attachments) {
        this.idUser = idUser;
        this.content = content;
        this.likes = likes;
        this.shares = shares;
        this.attachments = attachments;
    }

    public Publication(int id, int idUser, String content, int likes, int shares, String attachments) {
    }


    public int getPublicationID() {
        return publicationID;
    }

    public void setPublicationID(int publicationID) {
        this.publicationID = publicationID;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }
/*
    @Override
    public String toString() {
        return "Publication{" +
                "publicationID=" + publicationID +
                ", idUser=" + idUser +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", likes=" + likes +
                ", shares=" + shares +
                ", attachments='" + attachments + '\'' +
                '}';
    } */
@Override
public String toString() {
    return "User ID: " + idUser  + content + "\nLikes: " + likes + "\nShares: " + shares + "\nTimestamp: " + timestamp;
}

}
