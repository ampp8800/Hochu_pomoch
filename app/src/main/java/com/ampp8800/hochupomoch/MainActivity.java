package com.ampp8800.hochupomoch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
        toolbar.setTitle(""); //избавиться от этого костыля
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imageView = (ImageView) findViewById(R.id.iv_edit);
        imageView.setVisibility(View.INVISIBLE);
        TextView textView = (TextView) findViewById(R.id.tv_toolbar_name);
        textView.setText(R.string.help);
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
        //логика работы нижней панели навигации
        BottomNavigationLogic bottomNavigationLogic = new BottomNavigationLogic(this, (View) findViewById(R.id.bottom_navigation));
        bottomNavigationLogic.switchingSectionsByAccrual();

    }

    private void setInitialData() {
        helps.add(new ListItem((String) getText(R.string.children), R.drawable.children));
        helps.add(new ListItem((String) getText(R.string.adults), R.drawable.adults));
        helps.add(new ListItem((String) getText(R.string.elderly), R.drawable.elderly));
        helps.add(new ListItem((String) getText(R.string.animals), R.drawable.animals));
        helps.add(new ListItem((String) getText(R.string.events), R.drawable.events));

    }
}

