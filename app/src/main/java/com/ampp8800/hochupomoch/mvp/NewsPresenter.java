package com.ampp8800.hochupomoch.mvp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.app.HochuPomochApplication;
import com.ampp8800.hochupomoch.data.MyRetrofitClient;
import com.ampp8800.hochupomoch.data.NetworkNewsRepository;
import com.ampp8800.hochupomoch.db.AppDatabase;
import com.ampp8800.hochupomoch.db.NewsEntity;
import com.ampp8800.hochupomoch.ui.NetworkStateHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsView> {
    @NonNull
    private Disposable disposable;
    @NonNull
    private NetworkNewsRepository networkNewsRepository;

    @Override
    public void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadNews();
    }

    public void loadNews() {
        if (NetworkStateHelper.isConnected(HochuPomochApplication.getInstance())) {
            networkNewsRepository = NetworkNewsRepository.newInstance();
            disposable = MyRetrofitClient.getInstance()
                    .getStarredRepose()
                    .subscribeOn(Schedulers.io())
                    .map(newsItemModels -> {
                        networkNewsRepository.writeToDatabaseListOfNews(newsItemModels);
                        return newsItemModels;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<NewsItemModel>>() {
                        @Override
                        public void accept(List<NewsItemModel> newsItemModels) throws Exception {
                            Log.i("", "RxJava2: Response from server");
                            getViewState().refreshNewsListOnScreen(newsItemModels);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.i("", "RxJava: HTTP Error: " + throwable.getMessage());
                        }
                    });
        } else {
            AppDatabase appDatabase = HochuPomochApplication.getInstance().getDatabase();
            disposable = appDatabase.newsEntityDao().getAll()
                    .subscribeOn(Schedulers.io())
                    .map(newsEntities -> {
                        ArrayList<NewsItemModel> news = new ArrayList<>();
                        for (NewsEntity newsEntity : newsEntities) {
                            news.add(new NewsItemModel(newsEntity));
                        }
                        return news;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<NewsItemModel>>() {
                        @Override
                        public void accept(List<NewsItemModel> newsItemModels) throws Exception {
                            Log.i("", "RxJava2: Respose from database");
                            getViewState().refreshNewsListOnScreen(newsItemModels);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.i("", "RxJava: Database Error: " + throwable.getMessage());
                        }
                    });
        }
    }

}
