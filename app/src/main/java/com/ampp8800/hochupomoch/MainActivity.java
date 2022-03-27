package com.ampp8800.hochupomoch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ListItem> helps = new ArrayList<ListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceHelp) {
        super.onCreate(savedInstanceHelp);
        Context context = this;
        setContentView(R.layout.activity_help);
        // инициализация тулбара
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // начальная инициализация списка
        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.helps_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        // добавление тоста
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void invoke(String name) {
                Toast toast = Toast.makeText(context, name, Toast.LENGTH_SHORT);
                toast.show();
            }
        };
        // создаем адаптер
        HelpAdapter adapter = new HelpAdapter(context, helps, onItemClickListener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);

        //переделать это говно
        View view = (View) findViewById(R.id.profile_button);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }


    private void setInitialData() {
        helps.add(new ListItem("Дети", R.drawable.children));
        helps.add(new ListItem("Взрослые", R.drawable.adults));
        helps.add(new ListItem("Пожилые", R.drawable.elderly));
        helps.add(new ListItem("Животные", R.drawable.animals));
        helps.add(new ListItem("Мероприятия", R.drawable.events));

    }
}

