package com.ampp8800.hochupomoch.mvp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.app.HochuPomochApplication;
import com.ampp8800.hochupomoch.data.DatabaseNewsRepository;
import com.ampp8800.hochupomoch.db.AppDatabase;
import com.ampp8800.hochupomoch.db.NewsEntity;
import com.ampp8800.hochupomoch.ui.NetworkStateHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsView> {
    @NonNull
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private DatabaseNewsRepository databaseNewsRepository;
    @NonNull
    private static final String TAG = String.valueOf(NewsPresenter.class);

    @Override
    public void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadNews();
    }

    public void loadNews() {
        if (NetworkStateHelper.isConnected(HochuPomochApplication.getInstance())) {
            databaseNewsRepository = DatabaseNewsRepository.newInstance();
            compositeDisposable.add(HochuPomochApplication.getInstance()
                    .getNewsInformation()
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
                        getViewState().showToast(HochuPomochApplication.getInstance().getString(R.string.no_response_from_the_network));
                        loadNewsFromDatabase();
                    }));
        } else {
            loadNewsFromDatabase();
        }
    }

    private void loadNewsFromDatabase() {
        AppDatabase appDatabase = HochuPomochApplication.getInstance().getDatabase();
        compositeDisposable.add(appDatabase.newsEntityDao().getAll()
                .subscribeOn(Schedulers.io())
                .map(newsEntities -> {
                    ArrayList<NewsItemModel> news = new ArrayList<>();
                    for (NewsEntity newsEntity : newsEntities) {
                        news.add(new NewsItemModel(newsEntity));
                    }
                    return news;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Consumer<List<NewsItemModel>>) newsItemModels -> {
                    Log.i(TAG, "RxJava2: Respose from database");
                    getViewState().refreshNewsListOnScreen(newsItemModels);
                }, throwable -> {
                    Log.i(TAG, "RxJava: Database Error: " + throwable.getMessage());
                    getViewState().showToast(HochuPomochApplication.getInstance().getString(R.string.failed_to_load_news));
                }));
    }

}
