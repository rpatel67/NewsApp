package com.example.rkjc.news_app_2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    static final String TAG = "mainactivity";
    private ProgressBar progress;
    private RecyclerView mRecyclerView;
    private NewsRecyclerViewAdapter mNewsAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        NetworkTask task = new NetworkTask();
        task.execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        System.out.print(id);
        if (id == R.id.action_search) {
            Context context = MainActivity.this;
            String message = "Refresh Clicked";
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openPage(String url) {
        Uri webPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class NetworkTask extends AsyncTask<String, Void, ArrayList<NewsItem>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }
        @Override
        protected ArrayList<NewsItem> doInBackground(String... params) {
            ArrayList<NewsItem> mnewsData = null;

            URL newsRequestUrl = NetworkUtils.makeURL();
            try {
                String jsonNewsResponse = NetworkUtils
                        .getResponseFromHttpUrl(newsRequestUrl);

                mnewsData = JsonUtils.parseNews(jsonNewsResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mnewsData;
        }
        @Override
        protected void onPostExecute(final ArrayList<NewsItem> mnewsData) {
            super.onPostExecute(mnewsData);
            progress.setVisibility(View.GONE);
            if (mnewsData != null) {
                NewsRecyclerViewAdapter adapter = new NewsRecyclerViewAdapter(mnewsData, new NewsRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void OnItemClick(int clickedItem) {
                        String url = mnewsData.get(clickedItem).getUrl();
                        Log.d(TAG, String.format("Url %s", url));
                        openPage(url);
                    }

                });
                mRecyclerView.setAdapter(adapter);
            }
        }
    }
}
