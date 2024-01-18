package com.example.tk_recipe;

import static android.graphics.Color.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import java.util.ArrayList;
import java.util.List;

import static com.example.tk_recipe.beef_database.COLUMN_NAME_PICTURE;
import static com.example.tk_recipe.beef_database.COLUMN_NAME_SUBTITLE;
import static com.example.tk_recipe.beef_database.COLUMN_NAME_TITLE;
import static com.example.tk_recipe.beef_database.TABLE_NAME;
import static com.example.tk_recipe.beef_database._ID;

public class beef_recipe extends AppCompatActivity {

    public ListView listView;
    private List<String> dataList;
    private ListAdapter adapter;
    static final int REQUEST_IMAGE_PICK = 1;

    private EditText titleText, recipeText;
    private Button memoButton;
    private Button saveButton, removeButton;
    private beef_database dbHelper;
    private ImageView beefImage;
    private Uri selectedImageUri;
    private String currentImagePath;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beef_recipe);

        // データベースからデータを取得
        dbHelper = new beef_database(getApplicationContext());

        // ListView の設定get
        listView = findViewById(R.id.memoList);
        memoButton = findViewById(R.id.memo);

        // ボタンのクリックリスナー
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(beef_recipe.this, Memo_beef.class);
                startActivityForResult(intent, 1);
                finish();
            }
        };
        memoButton.setOnClickListener(onClickListener);


        // データベースからデータを取得してListViewに表示
        fetchDataAndDisplay();

        beef_database database = new beef_database(beef_recipe.this);
        SQLiteDatabase db = database.getReadableDatabase();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME_TITLE, COLUMN_NAME_SUBTITLE}, null, null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToPosition(position)) {


                        String data = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                        String data2 = cursor.getString(cursor.getColumnIndexOrThrow("recipe"));
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));

                        // Uri uri = dbHelper.getUriById(id);

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
                                -50);
                        titleParam.topMargin = 200;
                        titleText.setLayoutParams(titleParam);
                        titleText.setText(data);
                        titleText.setBackgroundColor(Color.WHITE);  // 背景色を白に設定
                        titleText.setTextColor(Color.BLACK);
                        titleText.setHint("Title Here");


                        recipeText = new EditText(beef_recipe.this);
                        RelativeLayout.LayoutParams recipeParams = new RelativeLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        recipeParams.topMargin = 1000;
                        recipeText.setLayoutParams(recipeParams);
                        recipeText.setText(data2);
                        recipeText.setBackgroundColor(Color.WHITE);  // 背景色を白に設定
                        recipeText.setTextColor(Color.BLACK);       // テキストの色を黒に設定
                        recipeText.setHint("Recipe Here");


                        saveButton = new Button(beef_recipe.this);
                        RelativeLayout.LayoutParams saveParams = new RelativeLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        saveParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        saveButton.setLayoutParams(saveParams);
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


                        removeButton = new Button(beef_recipe.this);
                        RelativeLayout.LayoutParams removeParmas = new RelativeLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );

                        // 画面の上部に配置
                        removeParmas.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                        removeParmas.addRule(RelativeLayout.CENTER_HORIZONTAL);
                        removeButton.setLayoutParams(removeParmas);
                        removeButton.setText("DELETE");


                        Button picAddButton = new Button(beef_recipe.this);
                        makeButtonRound(picAddButton);
                        RelativeLayout.LayoutParams picAddParams = new RelativeLayout.LayoutParams(
                                200, 200
                        );
                        picAddParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        picAddParams.topMargin = 1400;
                        picAddButton.setLayoutParams(picAddParams);
                        picAddButton.setText("写真");
                        picAddButton.setTextSize(20f);


                        beefImage = new ImageView(beef_recipe.this);
                        RelativeLayout.LayoutParams beefImageParams = new RelativeLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                1000
                        );
                        beefImageParams.topMargin = 1900;
                        beefImage.setLayoutParams(beefImageParams);
                        beefImage.setAdjustViewBounds(true);
                        String dataUri = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_PICTURE));
                        Uri ur1 = Uri.parse(dataUri);

                        beefImage.setImageURI(ur1);


                        NestedScrollView nestedScrollView = new NestedScrollView(beef_recipe.this);

                        RelativeLayout.LayoutParams a = new RelativeLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                        );
                        nestedScrollView.setLayoutParams(a);


                        nestedScrollView.addView(relativeLayout);
                        relativeLayout.addView(titleText);
                        relativeLayout.addView(recipeText);
                        relativeLayout.addView(saveButton);
                        relativeLayout.addView(backButton);
                        relativeLayout.addView(picAddButton);
                        relativeLayout.addView(removeButton);
                        relativeLayout.addView(beefImage);
                        setContentView(nestedScrollView);


                        saveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String newTitleData = titleText.getText().toString();
                                String newRecipeData = recipeText.getText().toString();

                                int newPosition = position + 1;
                                database.updateData(newPosition, newTitleData, newRecipeData, String.valueOf(selectedImageUri));
                                Intent intent = new Intent(beef_recipe.this, beef_recipe.class);
                                startActivity(intent);
                                finish();

                            }
                        });
                        backButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(beef_recipe.this, beef_recipe.class);
                                startActivity(intent);

                                finish();
                            }
                        });

                        removeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int newPositon = position + 1;
                                deleteData(newPositon);
                                Intent intent = new Intent(beef_recipe.this, beef_recipe.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        picAddButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                openImagePicker();
                            }
                        });
                    }
                    cursor.close();
                }

                //close
                database.close();

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
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
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME_TITLE, COLUMN_NAME_SUBTITLE, COLUMN_NAME_PICTURE}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE));
            String subtitle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_SUBTITLE));

            dataList.add(title + ": " + subtitle);
        }

        cursor.close();
        return dataList;
    }

    public void deleteData(int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // データベースから該当のデータを削除
        db.delete(TABLE_NAME, _ID + "=?", new String[]{String.valueOf(position)});

        db.close();

        // ListViewを更新
        fetchDataAndDisplay(); // または adapter.notifyDataSetChanged();
    }


    private void makeButtonRound(Button button) {
        // グラデーションの形状を設定
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);

        // ボタンの背景色を設定
        shape.setColor(getResources().getColor(R.color.colorAccent));

        // ボーダーの太さと色を設定
        shape.setStroke(2, getResources().getColor(R.color.colorPrimaryDark));

        // ボタンにグラデーションの形状を設定
        button.setBackground(shape);
    }

    private void saveUriToDatabase(Uri uri) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String picUri = String.valueOf(uri);
        values.put(COLUMN_NAME_PICTURE, picUri);
    }
}





