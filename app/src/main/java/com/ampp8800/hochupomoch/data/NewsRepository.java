package com.ampp8800.hochupomoch.data;

import android.os.AsyncTask;
import android.view.View;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.api.MessagesApi;
import com.ampp8800.hochupomoch.api.NewsModel;
import com.ampp8800.hochupomoch.ui.NewsAdapter;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewsRepository extends AsyncTask<Void, Void, Void> {
    private final ArrayList<NewsItem> news = new ArrayList<>();
    private View view;
    private NewsAdapter adapter;

    public  NewsRepository (View view, NewsAdapter adapter) {
        this.view = view;
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        view.findViewById(R.id.pb_progress_bar).setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        loadNews();
        view.findViewById(R.id.pb_progress_bar).setVisibility(View.GONE);
    }

    public void loadNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://eithernor.github.io/help-server/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        MessagesApi messagesApi = retrofit.create(MessagesApi.class);

        Call<List<NewsModel>> messages = messagesApi.messages();

        messages.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                news.clear();
                for (NewsModel item : response.body()) {
                    news.add(new NewsItem(item.getImages().get(0),
                            item.getFundName(),
                            item.getDescription(),
                            item.getStartDate(),
                            item.getEndDate()));
                }
                adapter.updateNewsListItems(news);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                System.out.println("failure " + t);
            }
        });
    }

}
