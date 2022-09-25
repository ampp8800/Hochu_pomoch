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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.data.ListItem;
import com.ampp8800.hochupomoch.mvp.EventDetailsPresenter;
import com.ampp8800.hochupomoch.mvp.EventDetailsView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class EventDetailsFragment extends MvpAppCompatFragment implements EventDetailsView {
    @NonNull
    private Activity activity;
    private static boolean isInitialized = false;
    @NonNull
    private static final String ARG_NEWS_ITEM_GUID = "newsItemGuid";

    @NonNull
    private TextView tvNewsName;
    @NonNull
    private TextView tvDate;
    @NonNull
    private TextView tvFundName;
    @NonNull
    private TextView tvAddress;
    @NonNull
    private TextView tvWriteToUs;
    @NonNull
    private TextView tvDescription;
    @NonNull
    private TextView tvGoToOrganizationWbsite;
    @NonNull
    private TextView tvToolbarName;
    @NonNull
    private TextView tvEventName;
    @NonNull
    private TextView tvFriendsCount;

    @NonNull
    private ImageView ivShare;
    @NonNull
    private ImageView ivIconBack;
    @NonNull
    private ImageView ivCardimage;
    @NonNull
    private ImageView ivCardimageOne;
    @NonNull
    private ImageView ivCardimageTwo;

    @NonNull
    private CircleImageView civFriend;
    @NonNull
    private CircleImageView civFriendOne;
    @NonNull
    private CircleImageView civFriendTwo;
    @NonNull
    private CircleImageView civFriendThree;
    @NonNull
    private CircleImageView civFriendFour;

    @NonNull
    private ListView lvPhoneNumbers;

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
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        activity = requireActivity();
        ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        tvNewsName = view.findViewById(R.id.tv_news_name);
        tvDate = view.findViewById(R.id.tv_date);
        tvFundName = view.findViewById(R.id.tv_fundName);
        tvAddress = view.findViewById(R.id.tv_address);
        tvWriteToUs = view.findViewById(R.id.tv_write_to_us);
        tvDescription = view.findViewById(R.id.tv_description);
        tvGoToOrganizationWbsite = view.findViewById(R.id.tv_go_to_organization_website);
        tvFriendsCount = view.findViewById(R.id.tv_friends_count);
        tvToolbarName = activity.findViewById(R.id.tv_toolbar_name);
        tvEventName = activity.findViewById(R.id.tv_event_name);
        ivShare = activity.findViewById(R.id.iv_share);
        ivIconBack = activity.findViewById(R.id.iv_icon_back);
        ivCardimage = view.findViewById(R.id.iv_cardimage);
        ivCardimageOne = view.findViewById(R.id.iv_cardimage_one);
        ivCardimageTwo = view.findViewById(R.id.iv_cardimage_two);
        civFriend = view.findViewById(R.id.civ_friend);
        civFriendOne = view.findViewById(R.id.civ_friend_one);
        civFriendTwo = view.findViewById(R.id.civ_friend_two);
        civFriendThree = view.findViewById(R.id.civ_friend_three);
        civFriendFour = view.findViewById(R.id.civ_friend_four);
        lvPhoneNumbers = view.findViewById(R.id.lv_phone_numbers);
        //тут ошибка
        if(!isInitialized){
            if (getArguments().getString(ARG_NEWS_ITEM_GUID) != null) {
                eventDetailsPresenter.loadNews(getArguments().getString(ARG_NEWS_ITEM_GUID));
                isInitialized = true;
            } else {
                throw new IllegalArgumentException("required identifier not passed");
            }
        }
        return view;
    }

    @Override
    public void setReceivedData(@NonNull NewsItemModel newsItemModel) {
        tvNewsName.setText(newsItemModel.getName());
        tvDate.setText(NewsDetailsDataConverter.getDate(newsItemModel.getStartDate(), newsItemModel.getEndDate(), getContext()));
        tvFundName.setText(newsItemModel.getFundName());
        tvAddress.setText(newsItemModel.getAddress());
        lvPhoneNumbers.setAdapter(getArrayAdapterWithPhoneNumbers(newsItemModel.getPhones()));
        tvWriteToUs.setOnClickListener(clickedView -> eventDetailsPresenter.sendEmail(newsItemModel));
        setImages(newsItemModel.getImages());
        tvDescription.setText(newsItemModel.getDescription());
        tvGoToOrganizationWbsite.setOnClickListener(clickedView ->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(newsItemModel.getWebsite()))));
        eventDetailsPresenter.setLineWithFriends();
        //actionBar elements
        tvToolbarName.setVisibility(View.GONE);
        ivShare.setVisibility(View.VISIBLE);
        tvEventName.setVisibility(View.VISIBLE);
        tvEventName.setText(newsItemModel.getName());
        ivIconBack.setOnClickListener(new View.OnClickListener() {
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
        List<ImageView> imageViews = new ArrayList<>(Arrays.asList(ivCardimage, ivCardimageOne, ivCardimageTwo));
        for (int i = 0; i < images.size(); i++) {
            if (i >= imageViews.size()) {
                break;
            }
            setPhotoFromNetwork(imageViews.get(i), images.get(i));
            imageViews.get(i).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setLineWithFriends(@NonNull List<ListItem> friends) {
        List<CircleImageView> circleImageViews = new ArrayList<>(Arrays.asList(civFriend, civFriendOne,
                civFriendTwo, civFriendThree, civFriendFour));
        for (int i = 0; i < friends.size(); i++) {
            if (i >= circleImageViews.size()) {
                break;
            }
            setPhotoFromNetwork(circleImageViews.get(i), friends.get(i).getImageViewURL());
            circleImageViews.get(i).setVisibility(View.VISIBLE);
        }
        if (friends.size() > circleImageViews.size()) {
            tvFriendsCount.setText("+" + (friends.size() - circleImageViews.size()));
        }
    }

    private void setPhotoFromNetwork(@NonNull ImageView imageView, @NonNull String imageViewURL) {
        Glide
                .with(activity)
                .load(imageViewURL)
                .placeholder(R.drawable.ic_no_photo)
                .into(imageView);
    }

    @Override
    public void sendEmail(@NonNull NewsItemModel newsItemModel) {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{newsItemModel.getEmail()});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.want_to_help));
        activity.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    @Override
    public void showToast(@NonNull String string) {
        Toast toast = Toast.makeText(getContext(), string, Toast.LENGTH_SHORT);
        toast.show();
    }

}
