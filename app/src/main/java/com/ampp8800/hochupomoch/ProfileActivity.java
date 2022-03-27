package com.ampp8800.hochupomoch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private ArrayList<ListItem> friends = new ArrayList<ListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceHelp) {
        super.onCreate(savedInstanceHelp);
        Context context = this;
        setContentView(R.layout.activity_profile);

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
