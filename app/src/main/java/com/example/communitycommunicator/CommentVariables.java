package com.example.communitycommunicator;

/**
 * Created by mAni on 13/05/2018.
 */

public class CommentVariables {

    private int id;
    private String postId;
    private String userName;
    private String comment;

    public CommentVariables(int id, String postId, String userName, String comment) {
        this.id = id;
        this.postId = postId;
        this.userName = userName;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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
}
