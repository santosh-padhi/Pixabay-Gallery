package com.spadhi.pixabaygallery.Helper;

import com.spadhi.pixabaygallery.Models.Image;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageJsonParser {
    public static Image convertJSONObjectToImageObject(JSONObject jsonObject) throws JSONException {
        Image image=new Image();
        int id = jsonObject.getInt("id");
        String previewURL = jsonObject.getString("previewURL");
        String tags = jsonObject.getString("tags");
        String imageURL = jsonObject.getString("webformatURL");
        int imageWidth = jsonObject.getInt("imageWidth");
        int imageHeight = jsonObject.getInt("imageHeight");
        String user = jsonObject.getString("user");
        String userImageURL = jsonObject.getString("userImageURL");
        int likes = jsonObject.getInt("likes");
        int downloads = jsonObject.getInt("downloads");
        int views = jsonObject.getInt("views");
        image.setId(id);
        image.setPreviewURL(previewURL);
        image.setTags(tags);
        image.setImageURL(imageURL);
        image.setImageWidth(imageWidth);
        image.setImageHeight(imageHeight);
        image.setUser(user);
        image.setUserImageURL(userImageURL);
        image.setLikes(likes);
        image.setDownloads(downloads);
        image.setViews(views);
        return image;
    }
}
