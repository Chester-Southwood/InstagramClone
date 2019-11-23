package com.example.instagram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.instagram.Post;
import com.example.instagram.PostsAdapter;
import com.example.instagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {

    protected RecyclerView rvPosts;
    private final        String TAG = "PostsFragment";
    protected PostsAdapter adapter;
    protected List<Post> postList;
    private SwipeRefreshLayout swipeRefreshLayoutout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvPosts = view.findViewById(R.id.rvPosts);

        postList = new ArrayList<>();
        //create the adapter
        adapter = new PostsAdapter(getContext(), postList);

        swipeRefreshLayoutout = view.findViewById(R.id.swipeContainer);
        swipeRefreshLayoutout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeRefreshLayoutout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "fetching new data!");
                adapter.clear();
                queryPost();
                adapter.notifyDataSetChanged();
                swipeRefreshLayoutout.setRefreshing(false);
            }
        });
        
        //create the data source
        rvPosts.setAdapter(adapter);
        //set the adapter on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        //set the manager layout on the
        queryPost();
    }


    protected void queryPost() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
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
