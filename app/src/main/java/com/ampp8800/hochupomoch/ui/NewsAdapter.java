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
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsListItemViewHolder> {
    private final LayoutInflater inflater;
    private final List<NewsItem> newsListItems;
    private final Context context;

    NewsAdapter(@NonNull Context context) {
        this.context = context;
        this.newsListItems = new ArrayList<>();
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
        holder.bind(news);
    }

    @Override
    public int getItemCount() {
        return newsListItems.size();
    }


    public class NewsListItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView photoNews;
        private final TextView newsHeadline;
        private final TextView briefDescriptionOfNews;
        private final TextView date;


        NewsListItemViewHolder(@NonNull View view) {
            super(view);
            photoNews = view.findViewById(R.id.iv_news_assets);
            newsHeadline = view.findViewById(R.id.tv_news_headline);
            briefDescriptionOfNews = view.findViewById(R.id.tv_brief_description_of_news);
            date = view.findViewById(R.id.tv_date);
        }

        public void bind(@NonNull NewsItem newsItem) {
            setImageFromServer(photoNews, newsItem.getPhotoNews());
            newsHeadline.setText(newsItem.getNewsHeadline());
            briefDescriptionOfNews.setText(newsItem.getBriefDescriptionOfNews());
            date.setText(newsItem.getDate());
        }
    }

    private void setImageFromServer(@NonNull ImageView targetImageView, @NonNull String imageViewURL) {
        Glide
                .with(context)
                .load(imageViewURL)
                .placeholder(R.drawable.ic_no_photo)
                .into(targetImageView);
    }

    public void updateNewsListItems(@NonNull List<NewsItem> newsListItems) {
        this.newsListItems.clear();
        this.newsListItems.addAll(newsListItems);
        notifyDataSetChanged();
    }
}
