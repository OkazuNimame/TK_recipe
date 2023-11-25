package com.example.tk_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class beef_recipe extends AppCompatActivity {

    Button memo;
    EditText memoEditText;
    private ListView memoListView; // XML レイアウトで定義した ListView
    private ArrayAdapter<String> memoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beef_recipe);
        memo = findViewById(R.id.memo);
        memoListView = findViewById(R.id.memoList);// XML レイアウトで定義した ListView の ID




        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Intent intent = new Intent(beef_recipe.this, Memo_beef.class);
                 startActivity(intent);
            }
        };
        memo.setOnClickListener(onClickListener);
    }
}