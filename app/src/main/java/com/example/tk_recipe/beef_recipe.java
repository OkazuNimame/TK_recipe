package com.example.tk_recipe;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.tk_recipe.database.COLUMN_NAME_SUBTITLE;
import static com.example.tk_recipe.database.COLUMN_NAME_TITLE;
import static com.example.tk_recipe.database.TABLE_NAME;

public class beef_recipe extends AppCompatActivity {

    private List<String> dataList;
    private ListAdapter adapter;

    public static ListView listView;
    private database dbHelper;

    private Button memoButton;

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
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==  1 && resultCode == Activity.RESULT_OK) {
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
}





