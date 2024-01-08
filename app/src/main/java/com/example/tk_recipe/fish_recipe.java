package com.example.tk_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

public class fish_recipe extends AppCompatActivity {
    public ListView fish_list;
    private List<String> fish_dataList;
    private ListAdapter adapter;
    private Button add_fish_button;
    private fish_database dbHelper;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_recipe);

        dbHelper = new fish_database(getApplicationContext());

        fish_list = findViewById(R.id.fish_list);
        add_fish_button = findViewById(R.id.add_fish_recipe_button);

        add_fish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fish_recipe.this, fish_memo.class);
                startActivity(intent);
                finish();
            }
        });
    }
}