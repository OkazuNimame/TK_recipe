package com.example.tk_recipe;

import static android.graphics.Color.WHITE;

import static com.example.tk_recipe.fish_database.COLUMN_NAME_SUBTITLE_FISH;
import static com.example.tk_recipe.fish_database.COLUMN_NAME_TITLE_FISH;
import static com.example.tk_recipe.fish_database.TABLE_NAME_FISH;
import static com.example.tk_recipe.fish_database._ID_FISH;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
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
        fetchDataAndDisplay();

        fish_database database = new fish_database(fish_recipe.this);
        SQLiteDatabase db = database.getWritableDatabase();
        fish_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pisition, long l) {
                Cursor cursor = db.query(TABLE_NAME_FISH, new String[]{COLUMN_NAME_TITLE_FISH, COLUMN_NAME_SUBTITLE_FISH}, null, null, null, null, null);

                if (cursor != null && cursor.moveToPosition(pisition)) {

                    String data = cursor.getString(cursor.getColumnIndexOrThrow("fish_title"));
                    String data2 = cursor.getString(cursor.getColumnIndexOrThrow("fish_recipe"));

                    NestedScrollView nestedScrollView = new NestedScrollView(fish_recipe.this);
                    ConstraintLayout constraintLayout = new ConstraintLayout(fish_recipe.this);

                    constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    ));

                    Drawable background = new ColorDrawable(WHITE);
                    constraintLayout.setBackground(background);

                    EditText new_title = new EditText(fish_recipe.this);
                    ConstraintLayout.LayoutParams newTitleParam = new ConstraintLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            -50
                    );
                    newTitleParam.topMargin = 140;
                    new_title.setLayoutParams(newTitleParam);
                    new_title.setText(data);
                    new_title.setTextColor(Color.BLACK);
                    new_title.setHint("Title Here");


                    EditText new_recipe = new EditText(fish_recipe.this);
                    ConstraintLayout.LayoutParams newRecipeParams = new ConstraintLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    );
                    newRecipeParams.topMargin = 250;
                    new_recipe.setLayoutParams(newRecipeParams);
                    new_recipe.setText(data2);
                    new_recipe.setTextColor(Color.BLACK);
                    new_recipe.setHint("Recipe Here");

                    Button newSaveButton = new Button(fish_recipe.this);
                    ConstraintLayout.LayoutParams saveParams = new ConstraintLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

                    newSaveButton.setLayoutParams(saveParams);
                    newSaveButton.setText("SAVE");
                    newSaveButton.setTextSize(20f);


                    Button newBackButton = new Button(fish_recipe.this);
                    ConstraintLayout.LayoutParams backParams = new ConstraintLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

                    newBackButton.setText("BACK");
                    newBackButton.setTextSize(20f);


                    Button newDeleteButton = new Button(fish_recipe.this);
                    ConstraintLayout.LayoutParams deleteParams = new ConstraintLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

                    newDeleteButton.setLayoutParams(deleteParams);
                    newDeleteButton.setText("DELETE");
                    newDeleteButton.setTextSize(20f);


                    newSaveButton.setId(View.generateViewId());
                    newBackButton.setId(View.generateViewId());
                    newDeleteButton.setId(View.generateViewId());


                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);


                    // newSaveButton の制約
                    constraintSet.connect(newSaveButton.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16);
                    constraintSet.connect(newSaveButton.getId(), ConstraintSet.TOP, newSaveButton.getId(), ConstraintSet.BOTTOM, 16);

// newBackButton の制約
                    constraintSet.connect(newBackButton.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16);
                    constraintSet.connect(newBackButton.getId(), ConstraintSet.TOP, newBackButton.getId(), ConstraintSet.BOTTOM, 16);

// newDeleteButton の制約
                    constraintSet.connect(newDeleteButton.getId(), ConstraintSet.TOP, newSaveButton.getId(), ConstraintSet.BOTTOM, 16);
                    constraintSet.connect(newDeleteButton.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16);
                    constraintSet.connect(newDeleteButton.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16);

                    constraintLayout.addView(new_title);
                    constraintLayout.addView(new_recipe);
                    constraintLayout.addView(newSaveButton);
                    constraintLayout.addView(newBackButton);
                    constraintLayout.addView(newDeleteButton);

                    setContentView(constraintLayout);

                }
            }
        });
    }

    private void fetchDataAndDisplay() {
        // データを取得
        fish_dataList = fetchDataFromDatabase();

        // ArrayAdapterを更新
        adapter = new ArrayAdapter<>(this, R.layout.list_color, fish_dataList);

        // ListViewにアダプターをセット
        fish_list.setAdapter(adapter);
    }

    private List<String> fetchDataFromDatabase() {
        List<String> dataList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_FISH, new String[]{COLUMN_NAME_TITLE_FISH, COLUMN_NAME_SUBTITLE_FISH}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE_FISH));
            String subtitle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_SUBTITLE_FISH));
            dataList.add(title + ": " + subtitle);
        }

        cursor.close();
        return dataList;
    }

    public void deleteData(int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // データベースから該当のデータを削除
        db.delete(TABLE_NAME_FISH, _ID_FISH + "=?", new String[]{String.valueOf(position)});

        db.close();

        // ListViewを更新
        fetchDataAndDisplay(); // または adapter.notifyDataSetChanged();
    }
}