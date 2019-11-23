package com.example.instagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE       = "image";
    public static final String KEY_USER        = "user";
    public static final String KEY_CREATED_AT  = "createdAt";

    public String getKeyDescription() {
        return (String)get(KEY_DESCRIPTION);
    }

    public void setKeyDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public String getCreatedAtDate() {
        return (String)get(KEY_CREATED_AT);
    }

    public void setCreatedAtDate(ParseUser createdAtDate) {
        put(KEY_USER, createdAtDate);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser parseUser) {
        put(KEY_USER, parseUser);
    }

    public ParseFile getMedia() {
        return getParseFile("media");
    }

    public void setMedia(ParseFile parseFile) {
        put("media", parseFile);
    }
}
