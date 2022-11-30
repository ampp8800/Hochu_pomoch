package com.ampp8800.hochupomoch.mvp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.app.RestApi;
import com.ampp8800.hochupomoch.data.DatabaseNewsRepository;
import com.ampp8800.hochupomoch.data.ProfileRepository;
import com.ampp8800.hochupomoch.db.AppDatabase;
import com.ampp8800.hochupomoch.db.NewsEntity;

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
    @NonNull
    private DatabaseNewsRepository databaseNewsRepository = DatabaseNewsRepository.newInstance();
    @NonNull
    private AppDatabase appDatabase = RestApi.getInstance().getDatabase();

    @NonNull
    private static final String TAG = EventDetailsPresenter.class.getSimpleName();

    private boolean isInitialized = false;

    public void loadNews(@NonNull String guid) {
        if (!isInitialized) {
            isInitialized = true;
            databaseNewsRepository = DatabaseNewsRepository.newInstance();
            compositeDisposable.add(RestApi.getInstance()
                    .getNewsInformation()
                    .getApi()
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
                        getViewState().showToast(RestApi.getInstance().getString(R.string.no_response_from_the_network));
                        loadNewsFromDatabase(guid);
                    }));
        }
    }

    private void loadNewsFromDatabase(@NonNull String guid) {
        compositeDisposable.add(appDatabase.newsEntityDao().selectNewsEntity(guid)
                .subscribeOn(Schedulers.io())
                .map(newsEntity -> newsEntityToNewsItemModel(newsEntity))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsItemModel -> {
                    Log.i(TAG, "RxJava2: Respose from database");
                    getViewState().setReceivedData(newsItemModel);
                }, throwable -> {
                    Log.i(TAG, "RxJava: Database Error: " + throwable.getMessage());
                    getViewState().showToast(RestApi.getInstance().getString(R.string.failed_to_load_news));
                }));
    }

    public void sendEmail(@NonNull NewsItemModel newsItemModel) {
        getViewState().sendEmail(newsItemModel);
    }

    public void setLineWithFriends() {
        getViewState().setLineWithFriends(ProfileRepository.getInstance().getFrendsList());
    }

    @Nullable
    public NewsItemModel getNewsItemModelFromList(@NonNull List<NewsItemModel> newsItemModels, @NonNull String guid) {
        for (NewsItemModel news : newsItemModels) {
            if (news.getGuid().equals(guid)) {
                return news;
            }
        }
        return null;
    }

    @NonNull
    private NewsItemModel newsEntityToNewsItemModel(@NonNull NewsEntity newsEntity) {
        return new NewsItemModel(newsEntity);
    }
}
