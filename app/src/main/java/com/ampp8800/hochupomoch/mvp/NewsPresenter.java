package com.ampp8800.hochupomoch.mvp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.app.HochuPomochApplication;
import com.ampp8800.hochupomoch.data.DatabaseNewsRepository;
import com.ampp8800.hochupomoch.data.MyRetrofitClient;
import com.ampp8800.hochupomoch.data.NetworkNewsRepository;
import com.ampp8800.hochupomoch.db.AppDatabase;
import com.ampp8800.hochupomoch.db.NewsEntityDao;
import com.ampp8800.hochupomoch.ui.NetworkStateHelper;
import com.ampp8800.hochupomoch.ui.NewsLoadingCallback;

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
    @NonNull
    private ArrayList<NewsItemModel> news;

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
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<NewsItemModel>>() {
                        @Override
                        public void accept(List<NewsItemModel> newsItemModels) throws Exception {
                            Log.i("", "RxJava2: Respose from server");
                            news = new ArrayList<>();
                            AppDatabase database = HochuPomochApplication.getInstance().getDatabase();
                            NewsEntityDao newsEntityDao = database.newsEntityDao();
                            if (newsEntityDao != null) {
                                newsEntityDao.clearAll(newsEntityDao.getAll());
                            }
                            for (NewsItemModel item : newsItemModels) {
                                news.add(item);
                                newsEntityDao.insert(networkNewsRepository.newsItemToNewsEntity(item));
                            }
                                       getViewState().refreshNewsListOnScreen(news);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.i("", "RxJava: HTTP Error: " + throwable.getMessage());
                        }
                    });
        } else {
            DatabaseNewsRepository.newInstance().loadNews(new NewsLoadingCallback() {
                @Override
                public void onNewsUpdate(@NonNull List newsListItems) {
                    getViewState().refreshNewsListOnScreen(newsListItems);
                }
            });
        }
    }

}
