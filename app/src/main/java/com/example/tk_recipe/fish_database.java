package com.example.tk_recipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class fish_database extends SQLiteOpenHelper {

    public static final String TABLE_NAME_FISH = "fishdb";
    public static final String COLUMN_NAME_TITLE_FISH = "fish_title";
    public static final String COLUMN_NAME_SUBTITLE_FISH = "fish_recipe";
    public static final String _ID_FISH = "fish_id";
    public static final String DATABASE_NAME_FISH = "Fish_recipe_database";
    public static final int DATABASE_VERSION = 4;
    public static final String FISH_SQL =
            "CREATE TABLE " + TABLE_NAME_FISH + "(" +
                    _ID_FISH + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_TITLE_FISH + " TEXT," +
                    COLUMN_NAME_SUBTITLE_FISH + " TEXT)";

    public fish_database(Context context) {
        super(context, DATABASE_NAME_FISH, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FISH);
        onCreate(sqLiteDatabase);

    }

    public void updateData(int id, String newData, String newData2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_TITLE_FISH, newData);
        contentValues.put(COLUMN_NAME_SUBTITLE_FISH, newData2);

        db.update(TABLE_NAME_FISH, contentValues, _ID_FISH + "= ?", new String[]{String.valueOf(id)});
        db.close();
    }

}
