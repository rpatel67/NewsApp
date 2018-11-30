package com.example.rkjc.news_app_2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {
    public static ArrayList<NewsItem> parseNews(String json) throws JSONException {
        ArrayList<NewsItem> mnewsData = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray articles = main.getJSONArray("articles");
        for (int i = 0; i < articles.length(); i++) {
            JSONObject item = articles.getJSONObject(i);
            String author = item.getString("author");
            String title = item.getString("title");
            String description = item.getString("description");
            String url = item.getString("url");
            String urlToImage = item.getString("urlToImage");
            String publishedAt = item.getString("publishedAt");
            NewsItem ni = new NewsItem(author, title, description, url, urlToImage, publishedAt);
            mnewsData.add(ni);
        }
        Log.d("JSONParser", "Size of ArrayList: " + mnewsData.size());
        return mnewsData;
    }
}

