package com.ampp8800.hochupomoch.data;

import android.os.AsyncTask;
import android.view.View;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.api.MessagesApi;
import com.ampp8800.hochupomoch.api.NewsModel;
import com.ampp8800.hochupomoch.ui.NewsAdapter;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewsRepository {
    private NewsRepository newsRepository;
    private static View view;
    private static NewsAdapter adapter;
    private ArrayList<NewsItem> news = new ArrayList<>();

    private NewsRepository(){}

    public static NewsRepository newInstance(View view, NewsAdapter adapter) {
        NewsRepository.view = view;
        NewsRepository.adapter = adapter;
        return new NewsRepository();
    }

    public void getNewsLoadingAsyncTask() {
        DateLoader dateLoader = new DateLoader();
        dateLoader.execute();
    }

    private class DateLoader extends AsyncTask<Void, Void, ArrayList<NewsItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.findViewById(R.id.pb_progress_bar).setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://eithernor.github.io/help-server/")
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
            MessagesApi messagesApi = retrofit.create(MessagesApi.class);
            Call<List<NewsModel>> messages = messagesApi.messages();
            try {
                news.clear();
                for (NewsModel item :  messages.execute().body()) {
                    news.add(new NewsItem(item.getImages().get(0),
                            item.getFundName(),
                            item.getDescription(),
                            item.getStartDate(),
                            item.getEndDate()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return news;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> news) {
            super.onPostExecute(news);
            adapter.updateNewsListItems(news);
            adapter.notifyDataSetChanged();
            view.findViewById(R.id.pb_progress_bar).setVisibility(View.GONE);
        }

    }
}
