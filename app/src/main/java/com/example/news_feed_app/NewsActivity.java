/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.news_feed_app;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
//https://newsapi.org/v1/articles?source=techcrunch&apiKey={a40c1e52dffd47f082c6af5f2f1ace96}
public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    public static final String LOG_TAG = NewsActivity.class.getName();
    private static final String USGS_REQUEST_URL = "https://content.guardianapis.com/search?q=debate%20AND%20(economy%20OR%20immigration%20education)&tag=politics/politics&from-date=2014-01-01&api-key=test";
    ArrayList<News> earthquakes = null;
    ListView earthquakeListView;
    TextView emptyView;
    ProgressBar progressBar;
    // Create a new {@link ArrayAdapter} of earthquakes
    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(R.id.list);
        emptyView = (TextView) findViewById(R.id.empty_view);
        progressBar = (ProgressBar) findViewById(R.id.loader);

        earthquakes = new ArrayList<>();

        adapter = new NewsAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
        earthquakeListView.setEmptyView(emptyView);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News selectedItem = adapter.getItem(position);

                Uri websiteURI = Uri.parse(selectedItem.getUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, websiteURI);

                startActivity(websiteIntent);
            }
        });

        LoaderManager loaderManager = getLoaderManager();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            loaderManager.initLoader(1, null, this);
        } else {
            progressBar.setVisibility(View.GONE);
            emptyView.setText("NO INTERNET");
        }
    }


    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {


        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> earthquakeItems) {
        adapter.clear();
        if (earthquakeItems != null && !earthquakeItems.isEmpty()) {
            adapter.addAll(earthquakeItems);
        }
        emptyView.setText("NO DATA");
        progressBar.setVisibility(View.GONE);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            progressBar.setVisibility(View.GONE);
            emptyView.setText("NO INTERNET");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }
}
