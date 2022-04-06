package com.ampp8800.hochupomoch;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private ArrayList<ProfileRepository.FriendListItem> friends = (ArrayList<ProfileRepository.FriendListItem>) ProfileRepository.getProfileRepository().getFrendsList();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceHelp) {
        super.onCreate(savedInstanceHelp);
        Context context = this;
        setContentView(R.layout.activity_profile);

        // инициализация тулбара
        textView = (TextView) findViewById(R.id.tv_toolbar_name);
        textView.setText(R.string.profile);
        // вставка изображения из репозитория
        setImageViewFromInternet(context, R.id.iv_profile, ProfileRepository.getImageViewURL());
        // вставка текста из репозитория
        textView = findViewById(R.id.tv_profile_name);
        textView.setText(ProfileRepository.getProfileRepository().getNameProfile());
        textView = findViewById(R.id.tv_date_of_birth);
        textView.setText(ProfileRepository.getProfileRepository().getDateOfBirth());
        textView = findViewById(R.id.tv_field_of_activity);
        textView.setText(ProfileRepository.getProfileRepository().getFieldOfActivity());
        // начальная инициализация списка
        RecyclerView recyclerView = findViewById(R.id.friends_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        // создаем адаптер
        FriendAdapter adapter = new FriendAdapter(context, friends);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        //логика работы нижней панели навигации
        BottomNavigationLogic bottomNavigationLogic = new BottomNavigationLogic(this, (View) findViewById(R.id.bottom_navigation));
        bottomNavigationLogic.switchingSectionsByAccrual();

    }


    private void setImageViewFromInternet(Context context, int idImageView, String imageViewURL) {
        ImageView targetImageView = (ImageView) findViewById(idImageView);
        Glide
                .with(context)
                .load(imageViewURL)
                .into(targetImageView);
    }

}
