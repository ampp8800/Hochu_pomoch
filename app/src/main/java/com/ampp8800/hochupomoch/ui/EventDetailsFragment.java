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

public class EventDetailsFragment extends Fragment {
    @NonNull
    private View view;
    @NonNull
    private Activity activity;
    @NonNull
    private static final String ARG_NEWS_ITEM_GUID = "newsItemGuid";

    @NonNull
    public static EventDetailsFragment newInstance(@NonNull String guid) {
        EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NEWS_ITEM_GUID, guid);
        eventDetailsFragment.setArguments(args);
        return eventDetailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        activity = requireActivity();
        String guid;
        if (saveInstanceState != null) {
            guid = saveInstanceState.getString(ARG_NEWS_ITEM_GUID);
        } else if (getArguments() != null) {
            guid = getArguments().getString(ARG_NEWS_ITEM_GUID);
        } else {
            return view;
        }
        NewsItemLoadingCallback newsItemLoadingCallback = new NewsItemLoadingCallback() {
            @Override
            public void onNewsItemUpdate(@NonNull NewsItemModel newsItemModel) {
                setUpAppBar(((AppCompatActivity) activity).getSupportActionBar(), newsItemModel);
                setReceivedData(newsItemModel);
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
        if (getArguments() != null) {
            onState.putString(ARG_NEWS_ITEM_GUID, getArguments().getString(ARG_NEWS_ITEM_GUID));
        }
    }

    private void setUpAppBar(@NonNull ActionBar actionBar, @NonNull NewsItemModel newsItemModel) {
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

    private void setReceivedData(@NonNull NewsItemModel newsItemModel) {
        ((TextView) view.findViewById(R.id.tv_news_name)).setText(newsItemModel.getName());
        ((TextView) view.findViewById(R.id.tv_date))
                .setText(NewsDetailsDataConverter.getDate(newsItemModel.getStartDate(), newsItemModel.getEndDate(), getContext()));
        ((TextView) view.findViewById(R.id.tv_fundName)).setText(newsItemModel.getFundName());
        ((TextView) view.findViewById(R.id.tv_address)).setText(newsItemModel.getAddress());
        ((ListView) view.findViewById(R.id.lv_phone_numbers))
                .setAdapter(getArrayAdapterWithPhoneNumbers(newsItemModel.getPhones()));
        view.findViewById(R.id.tv_write_to_us).
                setOnClickListener(clickedView -> sendEmail(newsItemModel));
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

    private void setPhotoFromNetwork(int idImageView, @NonNull String imageViewURL) {
        ImageView targetImageView = (ImageView) view.findViewById(idImageView);
        Glide
                .with(activity)
                .load(imageViewURL)
                .placeholder(R.drawable.ic_no_photo)
                .into(targetImageView);
    }

    private void sendEmail(@NonNull NewsItemModel newsItemModel) {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{newsItemModel.getEmail()});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.want_to_help));
        activity.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

}
