package tn.esprit.javafxproject.models;

import java.time.LocalDateTime;

public class Comment {
    private int commentId;
    private int publicationId;
    private int userId;
    private String content;
    private LocalDateTime timestamp;
    private int likes;
    private String attachments;
    private int parentCommentId;

    public Comment(int commentId, int publicationId, int userId, String content, LocalDateTime timestamp, int likes, String attachments, int parentCommentId) {
        this.commentId = commentId;
        this.publicationId = publicationId;
        this.userId = userId;
        this.content = content;
        this.timestamp = timestamp;
        this.likes = likes;
        this.attachments = attachments;
        this.parentCommentId = parentCommentId;
    }
    public Comment( int publicationId, int userId, String content, LocalDateTime timestamp, int likes, String attachments) {

        this.publicationId = publicationId;
        this.userId = userId;
        this.content = content;
        this.timestamp = timestamp;
        this.likes = likes;
        this.attachments = attachments;
        this.parentCommentId = parentCommentId;
    }


    public Comment(int i, int publicationID, int i1, String commentContent, Object o, int i2, Object object) {

    }

    public Comment(int publicationID, int i, String commentContent, Object o, int i1, Object o1) {
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp != null ? timestamp : LocalDateTime.now();
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public int getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(int parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}
