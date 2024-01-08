package com.example.tk_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import static com.example.tk_recipe.database.COLUMN_NAME_SUBTITLE;
import static com.example.tk_recipe.database.COLUMN_NAME_TITLE;
import static com.example.tk_recipe.database.TABLE_NAME;

public class Memo_beef extends AppCompatActivity {

    EditText title, recipe_content;
    Button save, back;
    private database dbHelper;
    private List<String> dataList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_beef);
        title = findViewById(R.id.title);
        save = findViewById(R.id.recipe_save);
        recipe_content = findViewById(R.id.recipe_content);
        dbHelper = new database(getApplicationContext());
        dataList = new ArrayList<>();
        back = findViewById(R.id.back);

        // ボタンクリック時の処理
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title_save = title.getText().toString();
                String content_save = recipe_content.getText().toString();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME_TITLE, title_save);
                values.put(COLUMN_NAME_SUBTITLE, content_save);
                db.insert(TABLE_NAME, null, values);


                Intent intent = new Intent(Memo_beef.this, beef_recipe.class);
                intent.putStringArrayListExtra("data", (ArrayList<String>) dataList);
                startActivityForResult(intent, 1);
                // 入力フィールドをクリア
                title.setText("");
                recipe_content.setText("");
                setResult(Activity.RESULT_OK);
                finish(); // 重要: Memo_beef アクティビティを終了する


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Memo_beef.this, beef_recipe.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public List<String> fetchDataFromDatabase() {
        List<String> dataList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME_TITLE, COLUMN_NAME_SUBTITLE}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE));
            String subtitle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_SUBTITLE));
            dataList.add(title + ": " + subtitle);
        }

        cursor.close();
        return dataList;
    }
}




