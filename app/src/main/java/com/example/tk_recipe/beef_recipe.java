package com.example.tk_recipe;

import static android.graphics.Color.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.tk_recipe.database.COLUMN_NAME_SUBTITLE;
import static com.example.tk_recipe.database.COLUMN_NAME_TITLE;
import static com.example.tk_recipe.database.TABLE_NAME;
import static com.example.tk_recipe.database._ID;

public class beef_recipe extends AppCompatActivity {

    public static ListView listView;
    private List<String> dataList;
    private ListAdapter adapter;
    private database dbHelper;

    private EditText titleText, recipeText;
    private Button memoButton;
    private Button saveButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beef_recipe);

        // データベースからデータを取得
        dbHelper = new database(getApplicationContext());

        // ListView の設定
        listView = findViewById(R.id.memoList);
        memoButton = findViewById(R.id.memo);

        // ボタンのクリックリスナー
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(beef_recipe.this, Memo_beef.class);
                startActivityForResult(intent, 1);
            }
        };
        memoButton.setOnClickListener(onClickListener);


        // データベースからデータを取得してListViewに表示
        fetchDataAndDisplay();

        database database = new database(beef_recipe.this);
        SQLiteDatabase db = database.getReadableDatabase();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME_TITLE, COLUMN_NAME_SUBTITLE}, null, null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToPosition(position)) {


                        String data = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                        String data2 = cursor.getString(cursor.getColumnIndexOrThrow("recipe"));

                        RelativeLayout relativeLayout = new RelativeLayout(beef_recipe.this);
                        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                        ));


                        Drawable background = new ColorDrawable(WHITE);
                        relativeLayout.setBackground(background);


                        titleText = new EditText(beef_recipe.this);
                        RelativeLayout.LayoutParams titleParam = new RelativeLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        titleParam.topMargin = 140;
                        titleText.setLayoutParams(titleParam);
                        titleText.setText(data);
                        titleText.setBackgroundColor(Color.WHITE);  // 背景色を白に設定
                        titleText.setTextColor(Color.BLACK);
                        titleText.setHint("Title Here");


                        recipeText = new EditText(beef_recipe.this);
                        RelativeLayout.LayoutParams recipeParams = new RelativeLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                        );
                        recipeParams.topMargin = 250;
                        recipeText.setLayoutParams(recipeParams);
                        recipeText.setText(data2);
                        recipeText.setBackgroundColor(Color.WHITE);  // 背景色を白に設定
                        recipeText.setTextColor(Color.BLACK);       // テキストの色を黒に設定
                        recipeText.setHint("Recipe Here");


                        saveButton = new Button(beef_recipe.this);
                        RelativeLayout.LayoutParams savaParams = new RelativeLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        savaParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT); // recipeTextの下に配置する
                        saveButton.setLayoutParams(savaParams);
                        saveButton.setText("SAVE");
                        saveButton.setTextSize(20f);


                        Button backButton = new Button(beef_recipe.this);
                        RelativeLayout.LayoutParams backParmas = new RelativeLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        backParmas.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        backButton.setLayoutParams(backParmas);
                        backButton.setText("BACK");
                        backButton.setTextSize(20f);


                        relativeLayout.addView(titleText);
                        relativeLayout.addView(recipeText);
                        relativeLayout.addView(saveButton);
                        relativeLayout.addView(backButton);


                        setContentView(relativeLayout);


                        saveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String newTitleData = titleText.getText().toString();
                                String newRecipeData = recipeText.getText().toString();

                                int newPosition = position + 1;
                                database.updateData(newPosition, newTitleData, newRecipeData);


                                Intent intent = new Intent(beef_recipe.this, beef_recipe.class);
                                startActivity(intent);


                            }
                        });
                    }
                    cursor.close();
                }

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
        adapter = new ArrayAdapter<>(this, R.layout.list_color, dataList);

        // ListViewにアダプターをセット
        listView.setAdapter(adapter);
    }

    private List<String> fetchDataFromDatabase() {
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

    /*private void showOriginalListView() {
        // 元のデータを保存
        originalDataList = new ArrayList<>(dataList);

        // 新しいレイアウトに切り替える
        setContentView(R.layout.activity_beef_recipe);

        // データを再設定
        fetchDataAndDisplay();
    }*/
}





