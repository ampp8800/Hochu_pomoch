package com.ampp8800.hochupomoch.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.data.NewsItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsListItemViewHolder> {
    private final LayoutInflater inflater;
    private final List<NewsItem> newsListItems;

    NewsAdapter(@NonNull Context context, @NonNull List<NewsItem> newsListItems) {
        this.newsListItems = newsListItems;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    @NonNull
    public NewsAdapter.NewsListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_item, parent, false);
        return new NewsAdapter.NewsListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsListItemViewHolder holder, int position) {
        NewsItem news = newsListItems.get(position);
        holder.photoNews.setImageResource(news.getPhotoNews());
        holder.newsHeadline.setText(news.getNewsHeadline());
        holder.briefDescriptionOfNews.setText(news.getBriefDescriptionOfNews());
        holder.date.setText(news.getDate());
    }

    @Override
    public int getItemCount() {
        return newsListItems.size();
    }

    public class NewsListItemViewHolder extends RecyclerView.ViewHolder {
        final ImageView photoNews;
        final TextView newsHeadline;
        final TextView briefDescriptionOfNews;
        final TextView date;


        NewsListItemViewHolder(@NonNull View view) {
            super(view);
            photoNews = view.findViewById(R.id.iv_news_assets);
            newsHeadline = view.findViewById(R.id.tv_news_headline);
            briefDescriptionOfNews = view.findViewById(R.id.tv_brief_description_of_news);
            date = view.findViewById(R.id.tv_date);
        }
    }
}
