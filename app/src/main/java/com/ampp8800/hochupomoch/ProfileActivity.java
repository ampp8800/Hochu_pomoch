package com.ampp8800.hochupomoch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private ArrayList<ListItem> friends = new ArrayList<ListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceHelp) {
        super.onCreate(savedInstanceHelp);
        Context context = this;
        setContentView(R.layout.activity_profile);

        // вставка изображения
        ImageView targetImageView = (ImageView) findViewById(R.id.profile_image);
        Glide
                .with(context)
                .load("https://sun9-63.userapi.com/impf/c625318/v625318902/28050/-l1-yQ4qIQk.jpg?size=720x1080&quality=96&sign=d1a87aef8827f52ed841888c14b40e17&type=album")
                .into(targetImageView);

        // начальная инициализация списка
        setInitialData();
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

    private void setInitialData() {
        friends.add(new ListItem("Дмитрий Валерьевич", R.drawable.avatar_1));
        friends.add(new ListItem("Евгений Александров", R.drawable.avatar_2));
        friends.add(new ListItem("Виктор Кузнецов", R.drawable.avatar_3));
        friends.add(new ListItem("Дмитрий Валерьевич", R.drawable.avatar_1));
        friends.add(new ListItem("Евгений Александров", R.drawable.avatar_2));
        friends.add(new ListItem("Виктор Кузнецов", R.drawable.avatar_3));

    }
}
