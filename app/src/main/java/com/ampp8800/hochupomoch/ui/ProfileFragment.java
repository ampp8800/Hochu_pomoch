package com.ampp8800.hochupomoch.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.data.AuthorizationRepository;
import com.ampp8800.hochupomoch.data.ListItem;
import com.ampp8800.hochupomoch.data.ProfileRepository;
import com.ampp8800.hochupomoch.mvp.ProfilePresenter;
import com.ampp8800.hochupomoch.mvp.ProfileView;
import com.bumptech.glide.Glide;

import java.util.List;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class ProfileFragment extends MvpAppCompatFragment implements ProfileView {
    @NonNull
    private Activity activity;
    @NonNull
    private final ProfileRepository repository = ProfileRepository.getInstance();
    @NonNull
    private final AuthorizationRepository authorizationRepository = AuthorizationRepository.getInstance();
    @NonNull
    private final List<ListItem> friends = repository.getFrendsList();
    @NonNull
    private final ListItem userListItem = repository.getUserListItem();

    @NonNull
    RecyclerView recyclerView;

    @NonNull
    private TextView tvProfileName;
    @NonNull
    private TextView tvDateOfBirth;
    @NonNull
    private TextView tvFieldOfActivity;
    @NonNull
    private TextView tvToolbarName;

    @NonNull
    private ImageView ivProfile;
    @NonNull
    private ImageView ivEdit;
    @NonNull
    private ImageView ivIconBack;

    @NonNull
    private Button bSignOut;

    @InjectPresenter
    ProfilePresenter profilePresenter;

    @NonNull
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        activity = requireActivity();
        ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        recyclerView = view.findViewById(R.id.friends_list);
        tvProfileName = view.findViewById(R.id.tv_profile_name);
        tvDateOfBirth = view.findViewById(R.id.tv_date_of_birth);
        tvFieldOfActivity = view.findViewById(R.id.tv_field_of_activity);
        tvToolbarName = activity.findViewById(R.id.tv_toolbar_name);
        ivProfile = view.findViewById(R.id.iv_profile);
        ivEdit = activity.findViewById(R.id.iv_edit);
        ivIconBack = activity.findViewById(R.id.iv_icon_back);
        bSignOut = view.findViewById(R.id.b_sign_out);
        profilePresenter.loadProfile();
        return view;
    }

    @Override
    public void showProfile() {
        setPhotoFromNetwork(ivProfile, userListItem.getImageViewURL());
        tvProfileName.setText(userListItem.getName());
        tvDateOfBirth.setText(userListItem.getDateOfBirth());
        tvFieldOfActivity.setText(userListItem.getFieldOfActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FriendAdapter adapter = new FriendAdapter(getContext(), friends);
        recyclerView.setAdapter(adapter);
        bSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AuthorizationActivity.class));
                authorizationRepository.setAuthorized(false);
            }
        });
        ivEdit.setVisibility(View.VISIBLE);
        ivIconBack.setVisibility(View.GONE);
        tvToolbarName.setText(R.string.profile);
    }

    private void setPhotoFromNetwork(@NonNull ImageView imageView, @NonNull String imageViewURL) {
        Glide
                .with(activity)
                .load(imageViewURL)
                .placeholder(R.drawable.ic_no_photo)
                .into(imageView);
    }

}
