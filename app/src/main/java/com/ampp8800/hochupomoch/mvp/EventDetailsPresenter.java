package com.ampp8800.hochupomoch.mvp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.app.HochuPomochApplication;
import com.ampp8800.hochupomoch.data.MyRetrofitClient;
import com.ampp8800.hochupomoch.data.NetworkNewsRepository;
import com.ampp8800.hochupomoch.data.ProfileRepository;
import com.ampp8800.hochupomoch.db.AppDatabase;
import com.ampp8800.hochupomoch.db.NewsEntityDao;
import com.ampp8800.hochupomoch.ui.NetworkStateHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class EventDetailsPresenter extends MvpPresenter<EventDetailsView> {
    @NonNull
    private Disposable disposable;
    @NonNull
    private NewsEntityDao newsEntityDao;
    @NonNull
    private NetworkNewsRepository networkNewsRepository;

    private boolean isInitialized = false;

    public void loadNews(@NonNull String guid) {
        if (!isInitialized) {
            isInitialized = true;
            if (NetworkStateHelper.isConnected(HochuPomochApplication.getInstance())) {
                networkNewsRepository = NetworkNewsRepository.newInstance();
                disposable = MyRetrofitClient.getInstance()
                        .getStarredRepose()
                        .subscribeOn(Schedulers.io())
                        .map(newsItemModels -> {
                            networkNewsRepository.writeToDatabaseListOfNews(newsItemModels);
                            return getNewsItemModelFromEthernet(guid);
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<NewsItemModel>() {
                            @Override
                            public void accept(NewsItemModel newsItemModel) throws Exception {
                                Log.i("", "RxJava2: Respose from server");
                                getViewState().setReceivedData(newsItemModel);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.i("", "RxJava: HTTP Error: " + throwable.getMessage());
                                getViewState().showToast(HochuPomochApplication.getInstance().getString(R.string.no_response_from_the_network));
                                loadNewsFromDatabase(guid);
                            }
                        });
            } else {
                loadNewsFromDatabase(guid);
            }
        }
    }

    private void loadNewsFromDatabase(@NonNull String guid) {
        AppDatabase appDatabase = HochuPomochApplication.getInstance().getDatabase();
        disposable = appDatabase.newsEntityDao().selectNewsEntity(guid)
                .subscribeOn(Schedulers.io())
                .map(newsEntity -> {
                    NewsItemModel news = new NewsItemModel(newsEntity);
                    return news;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsItemModel>() {
                    @Override
                    public void accept(NewsItemModel newsItemModel) throws Exception {
                        Log.i("", "RxJava2: Respose from database");
                        getViewState().setReceivedData(newsItemModel);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("", "RxJava: Database Error: " + throwable.getMessage());
                    }
                });
    }

    public void sendEmail(@NonNull NewsItemModel newsItemModel) {
        getViewState().sendEmail(newsItemModel);
    }

    public void setLineWithFriends() {
        getViewState().setLineWithFriends(ProfileRepository.getInstance().getFrendsList());
    }

    public NewsItemModel getNewsItemModelFromEthernet(@NonNull String guid) {
        NewsItemModel newsItemModel = null;
        try {
            newsEntityDao = HochuPomochApplication.getInstance().getDatabase().newsEntityDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsItemModel;
    }

}
