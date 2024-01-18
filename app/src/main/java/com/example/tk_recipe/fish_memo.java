package com.example.tk_recipe;

import static com.example.tk_recipe.fish_database.COLUMN_NAME_SUBTITLE_FISH;
import static com.example.tk_recipe.fish_database.COLUMN_NAME_TITLE_FISH;
import static com.example.tk_recipe.fish_database.TABLE_NAME_FISH;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class fish_memo extends AppCompatActivity {
    EditText fish_recipe_title, fish_recipe;
    Button fish_recipe_save_button, fish_back_button;
    private fish_database dbHelper;
    private List<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_memo);
        fish_recipe = findViewById(R.id.fish_recipe);
        fish_recipe_title = findViewById(R.id.fish_recipe_title);
        fish_recipe_save_button = findViewById(R.id.fish_recipe_save_button);
        fish_back_button = findViewById(R.id.fish_back_button);
        dbHelper = new fish_database(getApplicationContext());
        dataList = new ArrayList<>();

        fish_recipe_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fish_title_save = fish_recipe_title.getText().toString();
                String fish_recipe_save = fish_recipe.getText().toString();

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME_TITLE_FISH, fish_title_save);
                values.put(COLUMN_NAME_SUBTITLE_FISH, fish_recipe_save);
                db.insert(TABLE_NAME_FISH, null, values);

                Intent intent = new Intent(fish_memo.this, com.example.tk_recipe.fish_recipe.class);
                intent.putStringArrayListExtra("data", (ArrayList<String>) dataList);
                startActivityForResult(intent, 1);
                fish_recipe_title.setText("");
                fish_recipe.setText("");
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        fish_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fish_memo.this, fish_recipe.class);
                startActivity(intent);
                finish();
            }
        });

    }
}