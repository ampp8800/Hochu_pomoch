package com.ampp8800.hochupomoch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<HelpListItem> helps = new ArrayList<HelpListItem>();

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
        OnHelpItemClicked onHelpItemClicked = new OnHelpItemClicked() {
            @Override
            public void invoke(String name) {
                Toast toast = Toast.makeText(context, name, Toast.LENGTH_SHORT);
                toast.show();
            }
        };
        // создаем адаптер
        HelpAdapter adapter = new HelpAdapter(context, helps, onHelpItemClicked);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }

    private void setInitialData() {
        helps.add(new HelpListItem("Дети", R.drawable.children));
        helps.add(new HelpListItem("Взрослые", R.drawable.adults));
        helps.add(new HelpListItem("Пожилые", R.drawable.elderly));
        helps.add(new HelpListItem("Животные", R.drawable.animals));
        helps.add(new HelpListItem("Мероприятия", R.drawable.events));

    }
}