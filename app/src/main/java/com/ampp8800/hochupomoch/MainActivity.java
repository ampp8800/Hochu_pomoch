package com.ampp8800.hochupomoch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Help> helps = new ArrayList<Help>();
    @Override
    protected void onCreate(Bundle savedInstanceHelp) {
        super.onCreate(savedInstanceHelp);
        setContentView(R.layout.activity_help);
        // инициализация тулбара
        Toolbar toolbar =  (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // начальная инициализация списка
        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.helps_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        // создаем адаптер
        HelpAdapter adapter = new HelpAdapter(this, helps);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }
    private void setInitialData() {
        helps.add(new Help("Дети", R.drawable.children));
        helps.add(new Help("Взрослые", R.drawable.adults));
        helps.add(new Help("Пожилые", R.drawable.elderly));
        helps.add(new Help("Животные", R.drawable.animals));
        helps.add(new Help("Мероприятия", R.drawable.events));

    }
}