package com.ampp8800.hochupomoch.ui;

import android.app.Activity;
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

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.data.ListItem;
import com.ampp8800.hochupomoch.data.ProfileRepository;
import com.ampp8800.hochupomoch.mvp.EventDetailsPresenter;
import com.ampp8800.hochupomoch.mvp.EventDetailsView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class EventDetailsFragment extends MvpAppCompatFragment implements EventDetailsView {
    @NonNull
    private Activity activity;
    @NonNull
    private View view;
    @NonNull
    private static final String ARG_NEWS_ITEM_GUID = "newsItemGuid";

    @InjectPresenter
    EventDetailsPresenter eventDetailsPresenter;

    @NonNull
    public static EventDetailsFragment newInstance(@NonNull String guid) {
        EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NEWS_ITEM_GUID, guid);
        eventDetailsFragment.setArguments(args);
        return eventDetailsFragment;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        activity = requireActivity();
        if (getArguments().getString(ARG_NEWS_ITEM_GUID) != null) {
            eventDetailsPresenter.newsItemLoadingCallback(NetworkStateHelper.isConnected(getContext()), getArguments().getString(ARG_NEWS_ITEM_GUID));
        } else {
            throw new IllegalArgumentException("required identifier not passed");
        }
        return view;
    }

    @Override
    public void setReceivedData(@NonNull NewsItemModel newsItemModel) {
        ((TextView) view.findViewById(R.id.tv_news_name)).setText(newsItemModel.getName());
        ((TextView) view.findViewById(R.id.tv_date))
                .setText(NewsDetailsDataConverter.getDate(newsItemModel.getStartDate(), newsItemModel.getEndDate(), getContext()));
        ((TextView) view.findViewById(R.id.tv_fundName)).setText(newsItemModel.getFundName());
        ((TextView) view.findViewById(R.id.tv_address)).setText(newsItemModel.getAddress());
        ((ListView) view.findViewById(R.id.lv_phone_numbers))
                .setAdapter(getArrayAdapterWithPhoneNumbers(newsItemModel.getPhones()));
        view.findViewById(R.id.tv_write_to_us).
                setOnClickListener(clickedView -> eventDetailsPresenter.sendEmail(newsItemModel));
        setImages(newsItemModel.getImages());
        ((TextView) view.findViewById(R.id.tv_description)).setText(newsItemModel.getDescription());
        view.findViewById(R.id.tv_go_to_organization_website).
                setOnClickListener(clickedView
                        -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(newsItemModel.getWebsite()))));
        setLineWithFriends();
        //setupAppBar
        ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        activity.findViewById(R.id.tv_toolbar_name).setVisibility(View.GONE);
        activity.findViewById(R.id.iv_share).setVisibility(View.VISIBLE);
        activity.findViewById(R.id.tv_event_name).setVisibility(View.VISIBLE);
        ((TextView) activity.findViewById(R.id.tv_event_name)).setText(newsItemModel.getName());
        activity.findViewById(R.id.iv_icon_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    @NonNull
    private ArrayAdapter<String> getArrayAdapterWithPhoneNumbers(@NonNull List<Long> phoneNumbersOnLong) {
        List<String> phoneNumbersOnString = new ArrayList<>();
        for (Long phoneNumber : phoneNumbersOnLong) {
            phoneNumbersOnString.add(PhoneNumberUtils.formatNumber(phoneNumber.toString(), "RU"));
        }
        return new ArrayAdapter<>(activity, R.layout.phone_number_list_item, phoneNumbersOnString);
    }

    private void setImages(@NonNull List<String> images) {
        int[] idsImage = {R.id.iv_cardimage, R.id.iv_cardimage_one, R.id.iv_cardimage_two};
        for (int i = 0; i < images.size(); i++) {
            if (i >= idsImage.length) {
                break;
            }
            setPhotoFromNetwork(idsImage[i], images.get(i));
            view.findViewById(idsImage[i]).setVisibility(View.VISIBLE);
        }
    }

    private void setLineWithFriends() {
        List<ListItem> friends = ProfileRepository.getInstance().getFrendsList();
        int[] idsImageFriend = {R.id.civ_friend, R.id.civ_friend_one, R.id.civ_friend_two,
                R.id.civ_friend_three, R.id.civ_friend_four};
        for (int i = 0; i < friends.size(); i++) {
            if (i >= idsImageFriend.length) {
                break;
            }
            setPhotoFromNetwork(idsImageFriend[i], friends.get(i).getImageViewURL());
            view.findViewById(idsImageFriend[i]).setVisibility(View.VISIBLE);
        }
        if (friends.size() > idsImageFriend.length) {
            ((TextView) view.findViewById(R.id.tv_friends_count))
                    .setText("+" + (friends.size() - idsImageFriend.length));
        }
    }

    public void setPhotoFromNetwork(int idImageView, @NonNull String imageViewURL) {
        ImageView targetImageView = (ImageView) view.findViewById(idImageView);
        Glide
                .with(activity)
                .load(imageViewURL)
                .placeholder(R.drawable.ic_no_photo)
                .into(targetImageView);
    }

    @Override
    public void sendEmail(@NonNull NewsItemModel newsItemModel) {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{newsItemModel.getEmail()});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.want_to_help));
        activity.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

}
