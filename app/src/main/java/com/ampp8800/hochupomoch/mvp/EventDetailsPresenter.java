package com.ampp8800.hochupomoch.mvp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.app.HochuPomochApplication;
import com.ampp8800.hochupomoch.data.DatabaseNewsRepository;
import com.ampp8800.hochupomoch.data.ProfileRepository;
import com.ampp8800.hochupomoch.db.AppDatabase;
import com.ampp8800.hochupomoch.ui.NetworkStateHelper;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class EventDetailsPresenter extends MvpPresenter<EventDetailsView> {
    @NonNull
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private DatabaseNewsRepository databaseNewsRepository;
    @NonNull
    private static final String TAG = String.valueOf(EventDetailsPresenter.class);

    private boolean isInitialized = false;

    public void loadNews(@NonNull String guid) {
        if (!isInitialized) {
            isInitialized = true;
            if (NetworkStateHelper.isConnected(HochuPomochApplication.getInstance())) {
                databaseNewsRepository = DatabaseNewsRepository.newInstance();
                compositeDisposable.add(HochuPomochApplication.getInstance()
                        .getNewsInformation()
                        .subscribeOn(Schedulers.io())
                        .map(newsItemModels -> {
                            databaseNewsRepository.writeToDatabaseListOfNews(newsItemModels);
                            return getNewsItemModelFromList(newsItemModels, guid);
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(newsItemModel -> {
                            Log.i(TAG, "RxJava2: Respose from server");
                            getViewState().setReceivedData(newsItemModel);
                        }, throwable -> {
                            Log.i(TAG, "RxJava: HTTP Error: " + throwable.getMessage());
                            getViewState().showToast(HochuPomochApplication.getInstance().getString(R.string.no_response_from_the_network));
                            loadNewsFromDatabase(guid);
                        }));
            } else {
                loadNewsFromDatabase(guid);
            }
        }
    }

    private void loadNewsFromDatabase(@NonNull String guid) {
        AppDatabase appDatabase = HochuPomochApplication.getInstance().getDatabase();
        compositeDisposable.add(appDatabase.newsEntityDao().selectNewsEntity(guid)
                .subscribeOn(Schedulers.io())
                .map(newsEntity -> {
                    NewsItemModel news = new NewsItemModel(newsEntity);
                    return news;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsItemModel -> {
                    Log.i(TAG, "RxJava2: Respose from database");
                    getViewState().setReceivedData(newsItemModel);
                }, throwable -> {
                    Log.i(TAG, "RxJava: Database Error: " + throwable.getMessage());
                    getViewState().showToast(HochuPomochApplication.getInstance().getString(R.string.failed_to_load_news));
                }));
    }

    public void sendEmail(@NonNull NewsItemModel newsItemModel) {
        getViewState().sendEmail(newsItemModel);
    }

    public void setLineWithFriends() {
        getViewState().setLineWithFriends(ProfileRepository.getInstance().getFrendsList());
    }

    public NewsItemModel getNewsItemModelFromList(@NonNull List<NewsItemModel> newsItemModels, @NonNull String guid) {
        for (NewsItemModel news : newsItemModels) {
            if (news.getGuid().equals(guid)) {
                return news;
            }
        }
        return null;
    }

}
