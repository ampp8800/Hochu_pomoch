package com.ampp8800.hochupomoch.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.api.NewsItem;
import com.ampp8800.hochupomoch.data.DatabaseNewsRepository;
import com.ampp8800.hochupomoch.data.ListItem;
import com.ampp8800.hochupomoch.data.NetworkNewsRepository;
import com.ampp8800.hochupomoch.data.ProfileRepository;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class EvevntDetailFragment extends Fragment {
    @NonNull
    private View view;
    @NonNull
    private NewsItem newsItem;
    @NonNull
    private String guid;
    @NonNull
    private final String NEWS_ITEM_GUID = "newsItemGuid";

    @NonNull
    public static EvevntDetailFragment newInstance() {
        return new EvevntDetailFragment();
    }

    public void setGuid(@NonNull String guid){
        this.guid = guid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        if (saveInstanceState != null) {
            guid = saveInstanceState.getString(NEWS_ITEM_GUID);
        }
        if (isConnected(getContext())) {
            NetworkNewsRepository.newInstance().loadNews(new NewsLoadingCallback() {
                @Override
                public void onNewsUpdate(List list) {
                    loadingDataForCurrentFragment(list, guid);
                }
            });
        } else {
            DatabaseNewsRepository.newInstance().loadNews(new NewsLoadingCallback() {
                @Override
                public void onNewsUpdate(List list) {
                    loadingDataForCurrentFragment(list, guid);
                }
            });
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle onState) {
        super.onSaveInstanceState(onState);
        onState.putString(NEWS_ITEM_GUID, guid);
    }

    private void setUpAppBar(ActionBar actionBar) {
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        requireActivity().findViewById(R.id.tv_toolbar_name).setVisibility(View.GONE);
        requireActivity().findViewById(R.id.iv_share).setVisibility(View.VISIBLE);
        requireActivity().findViewById(R.id.tv_event_name).setVisibility(View.VISIBLE);
        ((TextView) requireActivity().findViewById(R.id.tv_event_name)).setText(newsItem.getName());
    }

    private void setReceivedData() {
        ((TextView) view.findViewById(R.id.tv_news_name)).setText(newsItem.getName());
        ((TextView) view.findViewById(R.id.tv_date)).setText(newsItem.getDate());
        ((TextView) view.findViewById(R.id.tv_fundName)).setText(newsItem.getFundName());
        ((TextView) view.findViewById(R.id.tv_address)).setText(newsItem.getAddress());
        ((ListView) view.findViewById(R.id.lv_phone_numbers)).setAdapter(getPhoneNumbers(newsItem.getPhones()));
        view.findViewById(R.id.tv_write_to_us).
                setOnClickListener(clickedView -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))));
        setImages(newsItem.getImages());
        ((TextView) view.findViewById(R.id.tv_description)).setText(newsItem.getDescription());
        view.findViewById(R.id.tv_go_to_organization_website).
                setOnClickListener(clickedView -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.getWebsite()))));
        setLineWithFriends();
    }

    @NonNull
    private ArrayAdapter<String> getPhoneNumbers(@NonNull List<Long> phoneNumbersOnLong) {
        List<String> phoneNumbersOnString = new ArrayList<>();
        for (Long phoneNumber : phoneNumbersOnLong) {
            phoneNumbersOnString.add(PhoneNumberUtils.formatNumber(phoneNumber.toString(), "RU"));
        }
        return new ArrayAdapter<>(requireActivity(), R.layout.phone_number_list_item, phoneNumbersOnString);
    }

    private void setImages(@NonNull List<String> images) {
        switch (images.size()) {
            case 0:
                view.findViewById(R.id.iv_cardimage).setVisibility(View.GONE);
            case 1:
                view.findViewById(R.id.iv_cardimage_one).setVisibility(View.GONE);
            case 2:
                view.findViewById(R.id.iv_cardimage_two).setVisibility(View.GONE);
        }
        switch (images.size()) {
            case 3:
                setPhotoFromNetwork(R.id.iv_cardimage_two, images.get(2));
            case 2:
                setPhotoFromNetwork(R.id.iv_cardimage_one, images.get(1));
            case 1:
                setPhotoFromNetwork(R.id.iv_cardimage, images.get(0));
        }
    }

    private void setLineWithFriends() {
        List<ListItem> friends = ProfileRepository.getInstance().getFrendsList();
        switch (friends.size()){
            case 5:
                setPhotoFromNetwork(R.id.civ_friend_four, friends.get(4).getImageViewURL());
            case 4:
                setPhotoFromNetwork(R.id.civ_friend_three, friends.get(3).getImageViewURL());
            case 3:
                setPhotoFromNetwork(R.id.civ_friend_two, friends.get(2).getImageViewURL());
            case 2:
                setPhotoFromNetwork(R.id.civ_friend_one, friends.get(1).getImageViewURL());
            case 1:
                setPhotoFromNetwork(R.id.civ_friend, friends.get(0).getImageViewURL());
        }
        if (friends.size() > 5) {
            ((TextView) view.findViewById(R.id.tv_friends_count)).setText("+" + (friends.size() - 5));
        }
    }

    private void setPhotoFromNetwork(int idImageView, @NonNull String imageViewURL) {
        ImageView targetImageView = (ImageView) view.findViewById(idImageView);
        Glide
                .with(requireActivity())
                .load(imageViewURL)
                .placeholder(R.drawable.ic_no_photo)
                .into(targetImageView);
    }

    private boolean isConnected(@NonNull Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private void loadingDataForCurrentFragment(@NonNull List<NewsItem> list, @NonNull String guid) {
        for (NewsItem currentItem : list) {
            if (currentItem.getGuid().equals(guid)){
                newsItem = currentItem;
                break;
            }
        }
        setUpAppBar(((AppCompatActivity) requireActivity()).getSupportActionBar());
        setReceivedData();
    }

}
