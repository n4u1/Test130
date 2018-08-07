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

    public ContentDTO(){}

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("contentType", contentType);
        result.put("title", title);
        result.put("description", description);
        result.put("postLikeCount", postLikeCount);
        result.put("isUserLike", isUserLike);

        return result;
    }
}
