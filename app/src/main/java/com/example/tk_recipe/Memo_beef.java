package com.example.tk_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class Memo_beef extends AppCompatActivity {
    EditText title;
    Button save, back;

    private ListView memoListView;
    private database helper;
    private ArrayAdapter<String> adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_beef);
        title = findViewById(R.id.title);
        save = findViewById(R.id.recipe_save);
        memoListView = findViewById(R.id.memoList);
        helper = new database(getApplicationContext());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helper.getWritableDatabase();  // Use getWritableDatabase() instead of getReadableDatabase()
                String title_save = title.getText().toString();

                ContentValues values = new ContentValues();
                values.put("title", title_save);
                db.insert("Beefdb", null, values);

                // Update the ListView after adding a new memo
                List<String> memoList = helper.getAllMemos();
                if (memoListView != null) {
                    adapter = new ArrayAdapter<>(Memo_beef.this, android.R.layout.simple_list_item_1, memoList);
                    memoListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                title.setText("");  // Clear the EditText after saving

                // Assuming you want to start a new activity (beef_recipe) after saving
                Intent intent = new Intent(Memo_beef.this, beef_recipe.class);
                startActivity(intent);
            }
        });
    }
}
