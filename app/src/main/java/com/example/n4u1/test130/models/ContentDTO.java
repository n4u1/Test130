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

    public ContentDTO(String imageUrl_0, String imageUrl_1, String imageUrl_2, String imageUrl_3, String imageUrl_4, String imageUrl_5, String imageUrl_6, String imageUrl_7, String imageUrl_8, String imageUrl_9, String title, String description, String descriptionVideo_1, String descriptionVideo_2, String descriptionVideo_3, String descriptionImage_1, String descriptionImage_2, String descriptionImage_3, String uid, String userID, String pollMode, String contentType, String contentId, boolean isUserLike, int postLikeCount, int contentHit, String contentKey) {
        this.imageUrl_0 = imageUrl_0;
        this.imageUrl_1 = imageUrl_1;
        this.imageUrl_2 = imageUrl_2;
        this.imageUrl_3 = imageUrl_3;
        this.imageUrl_4 = imageUrl_4;
        this.imageUrl_5 = imageUrl_5;
        this.imageUrl_6 = imageUrl_6;
        this.imageUrl_7 = imageUrl_7;
        this.imageUrl_8 = imageUrl_8;
        this.imageUrl_9 = imageUrl_9;
        this.title = title;
        this.description = description;
        this.descriptionVideo_1 = descriptionVideo_1;
        this.descriptionVideo_2 = descriptionVideo_2;
        this.descriptionVideo_3 = descriptionVideo_3;
        this.descriptionImage_1 = descriptionImage_1;
        this.descriptionImage_2 = descriptionImage_2;
        this.descriptionImage_3 = descriptionImage_3;
        this.uid = uid;
        this.userID = userID;
        this.pollMode = pollMode;
        this.contentType = contentType;
        this.contentId = contentId;
        this.isUserLike = isUserLike;
        this.postLikeCount = postLikeCount;
        this.contentHit = contentHit;
        this.contentKey = contentKey;
    }

    public ContentDTO(){}

    public int getItemViewType() {
        return itemViewType;
    }

    public void setItemViewType(int itemViewType) {
        this.itemViewType = itemViewType;
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
