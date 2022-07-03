package com.ampp8800.hochupomoch.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.data.AuthorizationRepository;
import com.ampp8800.hochupomoch.data.ListItem;
import com.ampp8800.hochupomoch.data.ProfileRepository;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProfileFragment extends Fragment {
    private final ProfileRepository repository = ProfileRepository.getInstance();
    private final AuthorizationRepository authorizationRepository = AuthorizationRepository.getInstance();
    private final List<ListItem> friends = repository.getFrendsList();
    private final ListItem userListItem = repository.getUserListItem();
    private Context context;
    private View view;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = view.getContext();
        setUpAppBar(((AppCompatActivity) getActivity()).getSupportActionBar());
        // вставка изображения из репозитория
        setImageViewFromInternet(R.id.iv_profile, userListItem.getImageViewURL());
        // вставка текста из репозитория
        ((TextView) view.findViewById(R.id.tv_profile_name)).setText(userListItem.getName());
        ((TextView) view.findViewById(R.id.tv_date_of_birth)).setText(userListItem.getDateOfBirth());
        ((TextView) view.findViewById(R.id.tv_field_of_activity)).setText(userListItem.getFieldOfActivity());
        // начальная инициализация списка
        RecyclerView recyclerView = view.findViewById(R.id.friends_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // создаем адаптер
        FriendAdapter adapter = new FriendAdapter(context, friends);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        //кнопак выхода из аккаунта
        ((View) view.findViewById(R.id.b_sign_out)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AuthorizationActivity.class));
                authorizationRepository.setAuthorized(false);
            }
        });
        return view;
    }

    private void setImageViewFromInternet(int idImageView, @NonNull String imageViewURL) {
        ImageView targetImageView = (ImageView) view.findViewById(idImageView);
        Glide
                .with(context)
                .load(imageViewURL)
                .placeholder(R.drawable.ic_no_photo)
                .into(targetImageView);
    }

    private void setUpAppBar(ActionBar actionBar) {
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActivity().findViewById(R.id.iv_edit).setVisibility(View.VISIBLE);
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.iv_icon_back);
        imageView.setVisibility(View.GONE);
        ((TextView) getActivity().findViewById(R.id.tv_toolbar_name)).setText(R.string.profile);
    }

}
