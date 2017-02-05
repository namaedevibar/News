package com.activity.devibar.newsfeedmidterm;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.util.List;

/**
 * Created by namai on 2/5/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mUrl;

    public NewsLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null){
            return null;
        }

        List<News> news = null;
        try {
            news = RequestUtils.fetchNewsDate(mUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return news;

    }
}


