package com.example.tk_recipe;

import static com.example.tk_recipe.beef_database.COLUMN_NAME_PICTURE;
import static com.example.tk_recipe.beef_database.COLUMN_NAME_SUBTITLE;
import static com.example.tk_recipe.beef_database.COLUMN_NAME_TITLE;
import static com.example.tk_recipe.beef_database.TABLE_NAME;
import static com.example.tk_recipe.beef_recipe.REQUEST_IMAGE_PICK;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Memo_beef extends AppCompatActivity {

    EditText title, recipe_content;
    static final int REQUEST_IMAGE_PICK = 1;
    Button save, back, photo_button;
    private List<String> dataList;
    private beef_database dbHelper;
    private Uri selectedImageUri;
    private ArrayAdapter<String> adapter;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_beef);
        title = findViewById(R.id.title);
        save = findViewById(R.id.recipe_save);
        recipe_content = findViewById(R.id.recipe_content);
        dbHelper = new beef_database(getApplicationContext());
        dataList = new ArrayList<>();
        back = findViewById(R.id.back);
        photo_button = findViewById(R.id.photo_button);

        // ボタンクリック時の処理
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title_save = title.getText().toString();
                String content_save = recipe_content.getText().toString();
                String uri_save = String.valueOf(selectedImageUri);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME_TITLE, title_save);
                values.put(COLUMN_NAME_SUBTITLE, content_save);
                values.put(COLUMN_NAME_PICTURE, uri_save);
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

        photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
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

    private void openImagePicker() {
        // ギャラリーアプリを開くIntent
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            // 選択された写真のUriを取得
            selectedImageUri = data.getData();
            imageView = findViewById(R.id.imageView);
            imageView.setImageURI(selectedImageUri);
            // ここで得られたUriを使って何かしらの処理を行う
            // 例えば、ImageViewに表示する、データベースに保存する、など
            // ...

            Toast.makeText(this, "写真が選択されました", Toast.LENGTH_SHORT).show();
        }
    }
}




