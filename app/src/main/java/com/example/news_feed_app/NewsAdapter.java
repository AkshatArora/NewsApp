package com.example.news_feed_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sanjit on 5/9/16.
 * Project: QuakeReport
 * */
public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, List<News> objects) {
        super(context, 0, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView = convertView;
        if (currentView == null) {
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, null);
        }

        News currentItem = getItem(position);

       TextView category=(TextView) currentView.findViewById(R.id.categoryid);
        TextView title=(TextView)currentView.findViewById(R.id.titleid);
       category.setText(currentItem.getcategory());
        title.setText(currentItem.gettitle());
        return currentView;
    }


}
