package com.activity.devibar.newsfeedmidterm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by namai on 2/5/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    Context context;

    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_layout, parent, false);
        }

        News currentNews = getItem(position);

        TextView tvTitle = (TextView) listItemView.findViewById(R.id.txtTitle);
        TextView tvAuthor = (TextView) listItemView.findViewById(R.id.txtAuthor);
        TextView tvDescription = (TextView) listItemView.findViewById(R.id.txtDescription);
        TextView tvPublishedAt = (TextView) listItemView.findViewById(R.id.txtPublished);
        ImageView tvImage = (ImageView) listItemView.findViewById(R.id.imageView);

        tvTitle.setText(currentNews.getTitle());
        tvAuthor.setText(currentNews.getAuthor());
        tvDescription.setText(currentNews.getDescription());
        tvPublishedAt.setText(currentNews.getPublishedAt());

        Picasso.with(context).load(currentNews.getUrlImage()).resize(600, 400).into(tvImage);

        return listItemView;
    }

}


