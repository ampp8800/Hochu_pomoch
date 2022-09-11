package com.ampp8800.hochupomoch.ui;

import android.content.Intent;
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
import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.data.DatabaseNewsRepository;
import com.ampp8800.hochupomoch.data.ListItem;
import com.ampp8800.hochupomoch.data.NetworkNewsRepository;
import com.ampp8800.hochupomoch.data.ProfileRepository;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class EevntDetailFragment extends Fragment {
    @NonNull
    private View view;
    @NonNull
    private NewsItemModel newsItemModel;
    @NonNull
    private static String guid;
    @NonNull
    private final String ARG_NEWS_ITEM_GUID = "newsItemGuid";
    private final int MAXIMUM_NUMBER_OF_FRIENDS_TO_SHOW = 5;

    @NonNull
    public static EevntDetailFragment newInstance(@NonNull String guid) {
        EevntDetailFragment.guid = guid;
        return new EevntDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        if (saveInstanceState != null) {
            guid = saveInstanceState.getString(ARG_NEWS_ITEM_GUID);
        }
        NewsItemLoadingCallback newsItemLoadingCallback = new NewsItemLoadingCallback() {
            @Override
            public void onNewsItemUpdate(@NonNull NewsItemModel nim) {
                newsItemModel = nim;
                setUpAppBar(((AppCompatActivity) requireActivity()).getSupportActionBar());
                setReceivedData();
            }
        };
        if (NetworkStateHelper.isConnected(getContext())) {
            NetworkNewsRepository.newInstance().loadItemNews(newsItemLoadingCallback, guid);
        } else {
            DatabaseNewsRepository.newInstance().loadItemNews(newsItemLoadingCallback, guid);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle onState) {
        super.onSaveInstanceState(onState);
        onState.putString(ARG_NEWS_ITEM_GUID, guid);
    }

    private void setUpAppBar(ActionBar actionBar) {
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        requireActivity().findViewById(R.id.tv_toolbar_name).setVisibility(View.GONE);
        requireActivity().findViewById(R.id.iv_share).setVisibility(View.VISIBLE);
        requireActivity().findViewById(R.id.tv_event_name).setVisibility(View.VISIBLE);
        ((TextView) requireActivity().findViewById(R.id.tv_event_name)).setText(newsItemModel.getName());
        requireActivity().findViewById(R.id.iv_icon_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    private void setReceivedData() {
        ((TextView) view.findViewById(R.id.tv_news_name)).setText(newsItemModel.getName());
        ((TextView) view.findViewById(R.id.tv_date))
                .setText(Converter.getDate(newsItemModel.getStartDate(), newsItemModel.getEndDate()));
        ((TextView) view.findViewById(R.id.tv_fundName)).setText(newsItemModel.getFundName());
        ((TextView) view.findViewById(R.id.tv_address)).setText(newsItemModel.getAddress());
        ((ListView) view.findViewById(R.id.lv_phone_numbers))
                .setAdapter(getArrayAdapterWithPhoneNumbers(newsItemModel.getPhones()));
        view.findViewById(R.id.tv_write_to_us).
                setOnClickListener(clickedView -> sendEmail());
        setImages(newsItemModel.getImages());
        ((TextView) view.findViewById(R.id.tv_description)).setText(newsItemModel.getDescription());
        view.findViewById(R.id.tv_go_to_organization_website).
                setOnClickListener(clickedView
                        -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(newsItemModel.getWebsite()))));
        setLineWithFriends();
    }

    @NonNull
    private ArrayAdapter<String> getArrayAdapterWithPhoneNumbers(@NonNull List<Long> phoneNumbersOnLong) {
        List<String> phoneNumbersOnString = new ArrayList<>();
        for (Long phoneNumber : phoneNumbersOnLong) {
            phoneNumbersOnString.add(PhoneNumberUtils.formatNumber(phoneNumber.toString(), "RU"));
        }
        return new ArrayAdapter<>(requireActivity(), R.layout.phone_number_list_item, phoneNumbersOnString);
    }

    private void setImages(@NonNull List<String> images) {
        for (int i = 1; i <= images.size(); i++) {
            switch (i) {
                case 1:
                    setPhotoFromNetwork(R.id.iv_cardimage, images.get(0));
                    view.findViewById(R.id.iv_cardimage).setVisibility(View.VISIBLE);
                    break;
                case 2:
                    setPhotoFromNetwork(R.id.iv_cardimage_one, images.get(1));
                    view.findViewById(R.id.iv_cardimage_one).setVisibility(View.VISIBLE);
                    break;
                case 3:
                    setPhotoFromNetwork(R.id.iv_cardimage_two, images.get(2));
                    view.findViewById(R.id.iv_cardimage_two).setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    private void setLineWithFriends() {
        List<ListItem> friends = ProfileRepository.getInstance().getFrendsList();
        for (int i = 1; i <= friends.size(); i++) {
            switch (i) {
                case 1:
                    setPhotoFromNetwork(R.id.civ_friend, friends.get(0).getImageViewURL());
                    view.findViewById(R.id.civ_friend).setVisibility(View.VISIBLE);
                    break;
                case 2:
                    setPhotoFromNetwork(R.id.civ_friend_one, friends.get(1).getImageViewURL());
                    view.findViewById(R.id.civ_friend_one).setVisibility(View.VISIBLE);
                    break;
                case 3:
                    setPhotoFromNetwork(R.id.civ_friend_two, friends.get(2).getImageViewURL());
                    view.findViewById(R.id.civ_friend_two).setVisibility(View.VISIBLE);
                    break;
                case 4:
                    setPhotoFromNetwork(R.id.civ_friend_three, friends.get(3).getImageViewURL());
                    view.findViewById(R.id.civ_friend_three).setVisibility(View.VISIBLE);
                    break;
                case 5:
                    setPhotoFromNetwork(R.id.civ_friend_four, friends.get(4).getImageViewURL());
                    view.findViewById(R.id.civ_friend_four).setVisibility(View.VISIBLE);
                    break;
            }
        }
        if (friends.size() > MAXIMUM_NUMBER_OF_FRIENDS_TO_SHOW) {
            ((TextView) view.findViewById(R.id.tv_friends_count))
                    .setText("+" + (friends.size() - MAXIMUM_NUMBER_OF_FRIENDS_TO_SHOW));
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

    private void sendEmail() {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{newsItemModel.getEmail()});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, requireActivity().getString(R.string.want_to_help));
        requireActivity().startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

}
