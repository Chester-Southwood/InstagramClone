package com.example.instagram.fragments;

import android.util.Log;

import com.example.instagram.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment {
    private final String TAG = "ProfileFragment";

    @Override
    protected void queryPost() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "ERROR with query");
                    e.printStackTrace();
                    return;
                }
                postList.addAll(objects);
                adapter.notifyDataSetChanged();

                for (int i = 0; i < objects.size(); i++) {
                    Post post = objects.get(i);
                    Log.d(TAG, "Post: " + post.getKeyDescription() + ", username: " + post.getUser().getUsername());
                }
            }
        });
    }
}
