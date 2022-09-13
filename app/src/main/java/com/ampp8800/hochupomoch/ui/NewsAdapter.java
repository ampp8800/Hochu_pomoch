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
import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.data.OnItemClickListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsListItemViewHolder> {
    private final LayoutInflater inflater;
    private final List<NewsItemModel> newsListItems;
    private final Context context;
    private final OnItemClickListener onItemClickListener;


    NewsAdapter(@NonNull Context context, @NonNull OnItemClickListener onItemClickListener) {
        this.context = context;
        this.newsListItems = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    @NonNull
    public NewsAdapter.NewsListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_item, parent, false);
        return new NewsAdapter.NewsListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsListItemViewHolder holder, int position) {
        NewsItemModel news = newsListItems.get(position);
        holder.bind(news);
    }

    @Override
    public int getItemCount() {
        return newsListItems.size();
    }


    public class NewsListItemViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final ImageView photoNews;
        private final TextView newsHeadline;
        private final TextView briefDescriptionOfNews;
        private final TextView date;


        NewsListItemViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
            photoNews = view.findViewById(R.id.iv_news_assets);
            newsHeadline = view.findViewById(R.id.tv_news_name);
            briefDescriptionOfNews = view.findViewById(R.id.tv_brief_description_of_news);
            date = view.findViewById(R.id.tv_date);
        }

        public void bind(@NonNull NewsItemModel newsItemModel) {
            setImageFromServer(photoNews, newsItemModel.getImages().get(0));
            newsHeadline.setText(newsItemModel.getFundName());
            briefDescriptionOfNews.setText(newsItemModel.getDescription());
            date.setText(NewsDetailsDataConverter.getDate(newsItemModel.getStartDate(), newsItemModel.getEndDate(), view.getContext()));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.invoke(newsItemModel.getGuid());
                }
            });
        }
    }

    private void setImageFromServer(@NonNull ImageView targetImageView, @NonNull String imageViewURL) {
        Glide
                .with(context)
                .load(imageViewURL)
                .placeholder(R.drawable.ic_no_photo)
                .into(targetImageView);
    }

    public void updateNewsListItems(@NonNull List<NewsItemModel> newsListItems) {
        this.newsListItems.clear();
        this.newsListItems.addAll(newsListItems);
        notifyDataSetChanged();
    }
}
