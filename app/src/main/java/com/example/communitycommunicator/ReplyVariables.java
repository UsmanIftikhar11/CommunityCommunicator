package com.example.communitycommunicator;

/**
 * Created by mAni on 13/05/2018.
 */

public class ReplyVariables {

    private int id;
    private String userName;
    private String comment;
    private String commentId;
    private String reply;

    public ReplyVariables(int id, String userName, String comment, String commentId, String reply) {
        this.id = id;
        this.userName = userName;
        this.comment = comment;
        this.commentId = commentId;
        this.reply = reply;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
