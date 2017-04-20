package com.example.news_feed_app;

/**
 * Created by Akshat Arora on 24-03-2017.
 */

public class News {
    private String title;
    private String category;
    private String murl;


News(String title,String category,String url){
murl=url;
   this.title=title;
    this.category=category;
}
public String gettitle()
{
    return title;
}
public String getcategory()
{
    return category;
}
    public String getUrl()
    {
        return murl;
    }



}
