package com.example.communitycommunicator;

/**
 * Created by mAni on 26/04/2018.
 */

public class Product {

    private int id;
    private String image;
    private String caption;
    private String category;
    private String userName;
    private String likes;
    private String dislike;
    private String date;

    public Product(int id, String image, String caption, String category, String userName, String likes, String dislike, String date) {
        this.id = id;
        this.image = image;
        this.caption = caption;
        this.category = category;
        this.userName = userName;
        this.likes = likes;
        this.dislike = dislike;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislike() {
        return dislike;
    }

    public void setDislike(String dislike) {
        this.dislike = dislike;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
