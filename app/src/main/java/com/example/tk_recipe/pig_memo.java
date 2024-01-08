package com.example.tk_recipe;

import static com.example.tk_recipe.Pig_database.COLUMN_NAME_SUBTITLE_PIG;
import static com.example.tk_recipe.Pig_database.COLUMN_NAME_TITLE_PIG;
import static com.example.tk_recipe.Pig_database.TABLE_NAME_PIG;
import static com.example.tk_recipe.database.COLUMN_NAME_SUBTITLE;
import static com.example.tk_recipe.database.COLUMN_NAME_TITLE;
import static com.example.tk_recipe.database.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class pig_memo extends AppCompatActivity {
    EditText title, recipe_content;
    Button save, back;
    private Pig_database dbHel;
    private List<String> dataList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pig_memo);
        title = findViewById(R.id.pig_recipe_title);
        recipe_content = findViewById(R.id.pig_recipe);
        save = findViewById(R.id.pig_recipe_save_button);
        back = findViewById(R.id.pig_back_button);
        dataList = new ArrayList<>();
        dbHel = new Pig_database(getApplicationContext());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title_save = title.getText().toString();
                String content_save = recipe_content.getText().toString();
                SQLiteDatabase db = dbHel.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME_TITLE_PIG, title_save);
                values.put(COLUMN_NAME_SUBTITLE_PIG, content_save);
                db.insert(TABLE_NAME_PIG, null, values);

                Intent intent = new Intent(pig_memo.this, pig_recipe.class);
                intent.putStringArrayListExtra("data", (ArrayList<String>) dataList);
                startActivityForResult(intent, 1);
                // 入力フィールドをクリア
                title.setText("");
                recipe_content.setText("");
                setResult(Activity.RESULT_OK);
                finish(); // 重要: Memo_beef アクティビティを終了する
            }
        });
    }
}