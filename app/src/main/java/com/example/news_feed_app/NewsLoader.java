package com.example.news_feed_app;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by sanjit on 12/9/16.
 * Project: QuakeReport
 */
public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mUrl;

    public NewsLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<News> earthquakeItems = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakeItems;
    }
}
