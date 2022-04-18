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
    private final ProfileRepository repository = ProfileRepository.getInstance();
    private final List<ListItem> friends = repository.getFrendsList();
    private final ListItem userListItem = repository.getUserListItem();
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceHelp) {
        super.onCreate(savedInstanceHelp);
        context = this;
        setContentView(R.layout.activity_profile);

        // инициализация тулбара
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = (ImageView) findViewById(R.id.iv_icon_back);
        imageView.setVisibility(View.GONE);
        ((TextView) findViewById(R.id.tv_toolbar_name)).setText(R.string.profile);
        // вставка изображения из репозитория
        setImageViewFromInternet(R.id.iv_profile, userListItem.getImageViewURL());
        // вставка текста из репозитория
        ((TextView) findViewById(R.id.tv_profile_name)).setText(userListItem.getName());
        ((TextView) findViewById(R.id.tv_date_of_birth)).setText(userListItem.getDateOfBirth());
        ((TextView) findViewById(R.id.tv_field_of_activity)).setText(userListItem.getFieldOfActivity());
        // начальная инициализация списка
        RecyclerView recyclerView = findViewById(R.id.friends_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        // создаем адаптер
        FriendAdapter adapter = new FriendAdapter(context, friends);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        //логика работы нижней панели навигации
        BottomNavigationLogic.initializeBottomBar((View) findViewById(R.id.bottom_navigation));

    }


    private void setImageViewFromInternet(@NonNull int idImageView, @NonNull String imageViewURL) {
        ImageView targetImageView = (ImageView) findViewById(idImageView);
        Glide
                .with(context)
                .load(imageViewURL)
                .placeholder(R.drawable.ic_no_photo)
                .into(targetImageView);
    }

    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        this.finish();
        startActivity(intent);
    }

}
