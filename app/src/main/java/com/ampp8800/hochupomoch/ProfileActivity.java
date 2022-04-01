package com.ampp8800.hochupomoch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private ArrayList<ProfileRepository.FriendListItem> friends = (ArrayList<ProfileRepository.FriendListItem>) ProfileRepository.getProfileRepository().getFrendsList();

    @Override
    protected void onCreate(Bundle savedInstanceHelp) {
        super.onCreate(savedInstanceHelp);
        Context context = this;
        setContentView(R.layout.activity_profile);

        // инициализация тулбара
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // вставка изображения из репозитория
        setImageViewFromInternet(context, R.id.profile_image, ProfileRepository.getImageViewURL());

        // вставка текста из репозитория
        TextView textView = findViewById(R.id.profile_text_name);
        textView.setText(ProfileRepository.getProfileRepository().getNameProfile());
        textView = findViewById(R.id.date_of_birth);
        textView.setText(ProfileRepository.getProfileRepository().getDateOfBirth());
        textView = findViewById(R.id.field_of_activity);
        textView.setText(ProfileRepository.getProfileRepository().getFieldOfActivity());

        // начальная инициализация списка
        RecyclerView recyclerView = findViewById(R.id.friends_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

        // создаем адаптер
        FriendAdapter adapter = new FriendAdapter(context, friends);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);

        //переделать это говно
        View view = (View) findViewById(R.id.help_button);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    private void setImageViewFromInternet(Context context, int idImageView, String imageViewURL) {
        ImageView targetImageView = (ImageView) findViewById(idImageView);
        Glide
                .with(context)
                .load(imageViewURL)
                .into(targetImageView);
    }

}