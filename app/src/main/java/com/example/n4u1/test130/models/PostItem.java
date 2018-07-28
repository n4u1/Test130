package com.example.n4u1.test130.models;

public class PostItem {

    boolean isUserLike;
    int postLikeCount;
    String userName;
    String postImgURL;
    String PostText;

    public PostItem(boolean isUserLike, int postLikeCount, String userName, String postImgURL, String postText) {
        this.isUserLike = isUserLike;
        this.postLikeCount = postLikeCount;
        this.userName = userName;
        this.postImgURL = postImgURL;
        PostText = postText;
    }

    public boolean isUserLike() {
        return isUserLike;
    }

    public int getPostLikeCount() {
        return postLikeCount;
    }

    public String getUserName() {
        return userName;
    }

    public String getPostImgURL() {
        return postImgURL;
    }

    public String getPostText() {
        return PostText;
    }
}
