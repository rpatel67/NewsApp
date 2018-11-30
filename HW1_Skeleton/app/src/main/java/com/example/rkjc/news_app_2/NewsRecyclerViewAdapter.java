package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

private ArrayList<NewsItem> mNewsData;
    ItemClickListener listener;
    public NewsRecyclerViewAdapter(ArrayList<NewsItem> mNewsData, ItemClickListener listener){
        this.mNewsData = mNewsData;
        this.listener = listener;
    }
    public interface ItemClickListener{
        void OnItemClick(int clickedItem);
    }
    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public TextView description;
        public TextView publishedAt;
        public NewsViewHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            description = (TextView)view.findViewById(R.id.description);
            publishedAt = (TextView)view.findViewById(R.id.published_at);
            view.setOnClickListener(this);
        }
        public void bind(int position) {
            NewsItem item = mNewsData.get(position);
            title.setText("Title: " + item.getTitle());
            description.setText("Description: " + item.getDescription());
            publishedAt.setText("Date: " + item.getPublishedAt());
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.OnItemClick(position);
        }
    }
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new NewsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(NewsViewHolder newsHolder, int position) {
        newsHolder.bind(position);
    }
    @Override
    public int getItemCount(){
        if (mNewsData==null)
            return 0;
        else
            return mNewsData.size();
    }
}

