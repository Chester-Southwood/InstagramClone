package com.example.instagram;


import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
        .applicationId("chesters-instagram-codepath")
        .clientKey("pumpkinInstragram")
        .server("http://chesters-instagram-codepath.herokuapp.com/parse")
        .build());

    }
}
