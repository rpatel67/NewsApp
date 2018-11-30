package com.example.rutvi.newsapi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.rutvi.newsapi.data.DBHelper;
import com.example.rutvi.newsapi.data.DatabaseUtils;
import com.example.rutvi.newsapi.data.NewsItem;


import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


class RefreshTasks {
    static final String TAG = "RefreshTasks";

    public static void refreshArticles(Context context) {
        ArrayList<com.example.rutvi.newsapi.NewsItem> result = null;
        URL url = NetworkUtils.makeURL();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        try {
            DatabaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            Log.d(TAG, "JSON: " + json);
            result = JsonParser.parseJSON(json);
            DatabaseUtils.bulkInsert(db, result);
        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();

    }
}
