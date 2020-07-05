/*
   Homework 05: News APP
   Group #: 28
   Saloni Gupta 801080992
   Renju Hanna Robin 801076715
*/
package com.example.homework005;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        News news = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item,parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewNewsTitle = convertView.findViewById(R.id.textViewNewsTitle);
            viewHolder.textViewNewsAuthor = convertView.findViewById(R.id.textViewNewsAuthor);
            viewHolder.textViewNewsDate = convertView.findViewById(R.id.textViewNewsDate);
            viewHolder.imageViewNews = convertView.findViewById(R.id.imageViewNews);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.textViewNewsTitle.setText(news.getTitle());
        viewHolder.textViewNewsAuthor.setText(news.getAuthor());
        viewHolder.textViewNewsDate.setText(news.getPublishedAt());
        if(!news.getUrlToImage().equals("") ) {
            Picasso.get().load(news.getUrlToImage()).into(viewHolder.imageViewNews);
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView textViewNewsTitle;
        TextView textViewNewsAuthor;
        TextView textViewNewsDate;
        ImageView imageViewNews;

    }
}
