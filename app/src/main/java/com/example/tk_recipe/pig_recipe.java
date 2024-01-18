package com.example.tk_recipe;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static com.example.tk_recipe.Pig_database.COLUMN_NAME_SUBTITLE_PIG;
import static com.example.tk_recipe.Pig_database.COLUMN_NAME_TITLE_PIG;
import static com.example.tk_recipe.Pig_database.TABLE_NAME_PIG;
import static com.example.tk_recipe.Pig_database._ID_PIG;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class pig_recipe extends AppCompatActivity {

    public ListView listView;
    Pig_database database;
    Button add_button;
    private List<String> dataList;
    private ListAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pig_recipe);
        database = new Pig_database(pig_recipe.this);
        add_button = findViewById(R.id.add_button);
        listView = findViewById(R.id.pig_list);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pig_recipe.this, pig_memo.class);
                startActivity(intent);
                finish();
            }
        });

        fetchDataAndDisplay();


        SQLiteDatabase db = database.getReadableDatabase();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cursor cursor = db.query(TABLE_NAME_PIG, new String[]{COLUMN_NAME_TITLE_PIG, COLUMN_NAME_SUBTITLE_PIG}, null, null, null, null, null);

                if (cursor != null && cursor.moveToPosition(position)) {
                    String pigData = cursor.getString(cursor.getColumnIndexOrThrow("pig_title"));
                    String pigData2 = cursor.getString(cursor.getColumnIndexOrThrow("pig_recipe"));

                    FrameLayout frameLayout = new FrameLayout(pig_recipe.this);
                    frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    ));

                    Drawable background = new ColorDrawable(WHITE);
                    frameLayout.setBackground(background);

                    EditText pig_title_text = new EditText(pig_recipe.this);
                    FrameLayout.LayoutParams pig_title_params = new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            -50
                    );
                    pig_title_params.topMargin = 140;
                    pig_title_text.setLayoutParams(pig_title_params);
                    pig_title_text.setText(pigData);
                    pig_title_text.setBackgroundColor(WHITE);
                    pig_title_text.setTextColor(BLACK);
                    pig_title_text.setHint("Title Here");


                    EditText pig_recipe_text = new EditText(pig_recipe.this);
                    FrameLayout.LayoutParams pig_recipe_params = new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    );
                    pig_recipe_params.topMargin = 250;
                    pig_recipe_text.setLayoutParams(pig_recipe_params);
                    pig_recipe_text.setText(pigData2);
                    pig_recipe_text.setBackgroundColor(WHITE);
                    pig_recipe_text.setTextColor(BLACK);
                    pig_title_text.setHint("Recipe Here");


                    Button pig_recipe_data_saveButton = new Button(pig_recipe.this);
                    FrameLayout.LayoutParams pig_recipe_saveParams = new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    pig_recipe_saveParams.gravity = Gravity.LEFT;
                    pig_recipe_data_saveButton.setLayoutParams(pig_recipe_saveParams);
                    pig_recipe_data_saveButton.setText("SAVE");
                    Typeface typeface = Typeface.createFromAsset(
                            getAssets(), "burebure.ttf"
                    );
                    pig_recipe_data_saveButton.setTypeface(typeface);
                    pig_recipe_data_saveButton.setTextSize(20f);


                    Button pig_recipe_backButton = new Button(pig_recipe.this);
                    FrameLayout.LayoutParams pig_recipe_backParams = new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    pig_recipe_backParams.gravity = Gravity.RIGHT;
                    pig_recipe_backButton.setLayoutParams(pig_recipe_backParams);
                    pig_recipe_backButton.setText("BACK");
                    pig_recipe_backButton.setTypeface(typeface);
                    pig_recipe_backButton.setTextSize(20f);


                    Button pig_recipe_deleteButton = new Button(pig_recipe.this);
                    FrameLayout.LayoutParams pig_recipe_deleteParams = new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    pig_recipe_deleteParams.gravity = android.view.Gravity.TOP | android.view.Gravity.CENTER_HORIZONTAL;
                    pig_recipe_deleteButton.setLayoutParams(pig_recipe_deleteParams);
                    pig_recipe_deleteButton.setText("DELETE");
                    pig_recipe_deleteButton.setTypeface(typeface);
                    pig_recipe_backButton.setTextSize(20f);

                    frameLayout.addView(pig_title_text);
                    frameLayout.addView(pig_recipe_text);
                    frameLayout.addView(pig_recipe_data_saveButton);
                    frameLayout.addView(pig_recipe_backButton);
                    frameLayout.addView(pig_recipe_deleteButton);

                    setContentView(frameLayout);


                    pig_recipe_data_saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String new_pig_title = pig_title_text.getText().toString();
                            String new_pig_recipe = pig_recipe_text.getText().toString();

                            int newPosition = position + 1;

                            //String s = Integer.toString(newPosition);

                            //Toast.makeText(pig_recipe.this,s,Toast.LENGTH_SHORT).show();

                            database.updateData(newPosition, new_pig_title, new_pig_recipe);
                            Intent intent = new Intent(pig_recipe.this, pig_recipe.class);
                            startActivity(intent);
                            finish();
                        }
                    });


                    pig_recipe_backButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(pig_recipe.this, pig_recipe.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    pig_recipe_deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int newPosition = position + 1;
                            deleteData(newPosition);
                            // String s = Integer.toString(newPosition);
                            // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(pig_recipe.this, pig_recipe.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                cursor.close();
                database.close();
            }

        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // Memo_beef からの結果を受け取った場合の処理
            fetchDataAndDisplay();
        }
    }

    private void fetchDataAndDisplay() {
        // データを取得
        dataList = fetchDataFromDatabase();

        // ArrayAdapterを更新
        adapter = new ArrayAdapter<>(pig_recipe.this, R.layout.list_color, dataList);

        // ListViewにアダプターをセット
        listView.setAdapter(adapter);
    }

    private List<String> fetchDataFromDatabase() {
        List<String> dataList = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_PIG, new String[]{COLUMN_NAME_TITLE_PIG, COLUMN_NAME_SUBTITLE_PIG}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE_PIG));
            String subtitle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_SUBTITLE_PIG));
            dataList.add(title + ": " + subtitle);
        }

        cursor.close();
        return dataList;
    }

    public void deleteData(int position) {
        SQLiteDatabase db = database.getWritableDatabase();

        // データベースから該当のデータを削除
        db.delete(TABLE_NAME_PIG, _ID_PIG + "=?", new String[]{String.valueOf(position)});

        db.close();

        // ListViewを更新
        fetchDataAndDisplay(); // または adapter.notifyDataSetChanged();
    }
}