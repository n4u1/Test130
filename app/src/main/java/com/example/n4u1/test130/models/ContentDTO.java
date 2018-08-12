package com.example.n4u1.test130.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentDTO {
//    public String[] imageUrl = null;
    public String imageUrl_0, imageUrl_1, imageUrl_2, imageUrl_3, imageUrl_4, imageUrl_5,
                imageUrl_6, imageUrl_7, imageUrl_8, imageUrl_9;
    public String title;
    public String description;
    public String descriptionVideo_1, descriptionVideo_2, descriptionVideo_3;
    public String descriptionImage_1, descriptionImage_2, descriptionImage_3;
    public String uid;
    public String userID;
    public String pollMode;
    public String contentType;
    public String contentId;
    boolean isUserLike;
    int postLikeCount;
    int contentHit;
    public String contentKey;
    public int itemViewType;
    public String uploadDate;


    public ContentDTO(){}

    public String getImageUrl_0() {
        return imageUrl_0;
    }

    public void setImageUrl_0(String imageUrl_0) {
        this.imageUrl_0 = imageUrl_0;
    }

    public String getImageUrl_1() {
        return imageUrl_1;
    }

    public void setImageUrl_1(String imageUrl_1) {
        this.imageUrl_1 = imageUrl_1;
    }

    public String getImageUrl_2() {
        return imageUrl_2;
    }

    public void setImageUrl_2(String imageUrl_2) {
        this.imageUrl_2 = imageUrl_2;
    }

    public String getImageUrl_3() {
        return imageUrl_3;
    }

    public void setImageUrl_3(String imageUrl_3) {
        this.imageUrl_3 = imageUrl_3;
    }

    public String getImageUrl_4() {
        return imageUrl_4;
    }

    public void setImageUrl_4(String imageUrl_4) {
        this.imageUrl_4 = imageUrl_4;
    }

    public String getImageUrl_5() {
        return imageUrl_5;
    }

    public void setImageUrl_5(String imageUrl_5) {
        this.imageUrl_5 = imageUrl_5;
    }

    public String getImageUrl_6() {
        return imageUrl_6;
    }

    public void setImageUrl_6(String imageUrl_6) {
        this.imageUrl_6 = imageUrl_6;
    }

    public String getImageUrl_7() {
        return imageUrl_7;
    }

    public void setImageUrl_7(String imageUrl_7) {
        this.imageUrl_7 = imageUrl_7;
    }

    public String getImageUrl_8() {
        return imageUrl_8;
    }

    public void setImageUrl_8(String imageUrl_8) {
        this.imageUrl_8 = imageUrl_8;
    }

    public String getImageUrl_9() {
        return imageUrl_9;
    }

    public void setImageUrl_9(String imageUrl_9) {
        this.imageUrl_9 = imageUrl_9;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionVideo_1() {
        return descriptionVideo_1;
    }

    public void setDescriptionVideo_1(String descriptionVideo_1) {
        this.descriptionVideo_1 = descriptionVideo_1;
    }

    public String getDescriptionVideo_2() {
        return descriptionVideo_2;
    }

    public void setDescriptionVideo_2(String descriptionVideo_2) {
        this.descriptionVideo_2 = descriptionVideo_2;
    }

    public String getDescriptionVideo_3() {
        return descriptionVideo_3;
    }

    public void setDescriptionVideo_3(String descriptionVideo_3) {
        this.descriptionVideo_3 = descriptionVideo_3;
    }

    public String getDescriptionImage_1() {
        return descriptionImage_1;
    }

    public void setDescriptionImage_1(String descriptionImage_1) {
        this.descriptionImage_1 = descriptionImage_1;
    }

    public String getDescriptionImage_2() {
        return descriptionImage_2;
    }

    public void setDescriptionImage_2(String descriptionImage_2) {
        this.descriptionImage_2 = descriptionImage_2;
    }

    public String getDescriptionImage_3() {
        return descriptionImage_3;
    }

    public void setDescriptionImage_3(String descriptionImage_3) {
        this.descriptionImage_3 = descriptionImage_3;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPollMode() {
        return pollMode;
    }

    public void setPollMode(String pollMode) {
        this.pollMode = pollMode;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public boolean isUserLike() {
        return isUserLike;
    }

    public void setUserLike(boolean userLike) {
        isUserLike = userLike;
    }

    public int getPostLikeCount() {
        return postLikeCount;
    }

    public void setPostLikeCount(int postLikeCount) {
        this.postLikeCount = postLikeCount;
    }

    public int getContentHit() {
        return contentHit;
    }

    public void setContentHit(int contentHit) {
        this.contentHit = contentHit;
    }

    public String getContentKey() {
        return contentKey;
    }

    public void setContentKey(String contentKey) {
        this.contentKey = contentKey;
    }

    public int getItemViewType() {
        return itemViewType;
    }

    public void setItemViewType(int itemViewType) {
        this.itemViewType = itemViewType;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("contentType", contentType);
        result.put("title", title);
        result.put("description", description);
        result.put("postLikeCount", postLikeCount);

        return result;
    }
}
