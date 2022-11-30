package com.ampp8800.hochupomoch.mvp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.app.RestApi;
import com.ampp8800.hochupomoch.data.DatabaseNewsRepository;
import com.ampp8800.hochupomoch.db.AppDatabase;
import com.ampp8800.hochupomoch.db.NewsEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsView> {
    @NonNull
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    @NonNull
    private DatabaseNewsRepository databaseNewsRepository = DatabaseNewsRepository.newInstance();
    @NonNull
    private AppDatabase appDatabase = RestApi.getInstance().getDatabase();
    @NonNull
    private static final String TAG = NewsPresenter.class.getSimpleName();

    @Override
    public void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadNews();
    }

    public void loadNews() {
        compositeDisposable.add(RestApi.getInstance()
                .getNewsInformation()
                .getApi()
                .subscribeOn(Schedulers.io())
                .map(newsItemModels -> {
                    databaseNewsRepository.writeToDatabaseListOfNews(newsItemModels);
                    return newsItemModels;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsItemModels -> {
                    Log.i(TAG, "RxJava2: Response from server");
                    getViewState().refreshNewsListOnScreen(newsItemModels);
                }, throwable -> {
                    Log.i(TAG, "RxJava: HTTP Error: " + throwable.getMessage());
                    getViewState().showToast(RestApi.getInstance().getString(R.string.no_response_from_the_network));
                    loadNewsFromDatabase();
                }));
    }

    private void loadNewsFromDatabase() {
        compositeDisposable.add(appDatabase.newsEntityDao().getAll()
                .subscribeOn(Schedulers.io())
                .map(newsEntities -> newsEntityListToNewsItemModelList(newsEntities))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsItemModels -> {
                    Log.i(TAG, "RxJava2: Respose from database");
                    getViewState().refreshNewsListOnScreen(newsItemModels);
                }, throwable -> {
                    Log.i(TAG, "RxJava: Database Error: " + throwable.getMessage());
                    getViewState().showToast(RestApi.getInstance().getString(R.string.failed_to_load_news));
                }));
    }

    @NonNull
    private List<NewsItemModel> newsEntityListToNewsItemModelList(@NonNull List<NewsEntity> newsEntities) {
        ArrayList<NewsItemModel> news = new ArrayList<>();
        for (NewsEntity newsEntity : newsEntities) {
            news.add(new NewsItemModel(newsEntity));
        }
        return news;
    }

}
