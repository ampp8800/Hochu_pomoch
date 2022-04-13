package com.ampp8800.hochupomoch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private List<ListItem> friends = ProfileRepository.getInstance().getFrendsList();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceHelp) {
        super.onCreate(savedInstanceHelp);
        Context context = this;
        setContentView(R.layout.activity_profile);

        // инициализация тулбара
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = (ImageView) findViewById(R.id.iv_icon_back);
        imageView.setVisibility(View.GONE);
        textView = (TextView) findViewById(R.id.tv_toolbar_name);
        textView.setText(R.string.profile);
        // вставка изображения из репозитория
        setImageViewFromInternet(context, R.id.iv_profile, ProfileRepository.getImageViewUrl());
        // вставка текста из репозитория
        textView = findViewById(R.id.tv_profile_name);
        textView.setText(ProfileRepository.getInstance().getNameProfile());
        textView = findViewById(R.id.tv_date_of_birth);
        textView.setText(ProfileRepository.getInstance().getDateOfBirth());
        textView = findViewById(R.id.tv_field_of_activity);
        textView.setText(ProfileRepository.getInstance().getFieldOfActivity());
        // начальная инициализация списка
        RecyclerView recyclerView = findViewById(R.id.friends_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        // создаем адаптер
        FriendAdapter adapter = new FriendAdapter(context, friends);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        //логика работы нижней панели навигации
        BottomNavigationLogic.switchingSectionsByAccrual(this, (View) findViewById(R.id.bottom_navigation));

    }


    private void setImageViewFromInternet(@NonNull Context context, @NonNull int idImageView, @NonNull String imageViewURL) {
        ImageView targetImageView = (ImageView) findViewById(idImageView);
        Glide
                .with(context)
                .load(imageViewURL)
                .into(targetImageView);
    }

    public void onBackPressed() {
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }

    public void logout(View view) {
        startActivity(new Intent(ProfileActivity.this, AuthorizationActivity.class));
        ProfileRepository.setAuthorization("","");
    }

}
