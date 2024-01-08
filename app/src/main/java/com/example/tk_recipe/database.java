package com.example.tk_recipe;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class database extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Beefdb";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_SUBTITLE = "recipe";
    public static final String _ID = "_id";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME_TITLE + " TEXT, " +
                    COLUMN_NAME_SUBTITLE + " TEXT)";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final int NEW_DATABASE_VERSION = 4; // バージョンを更新
    private static final String NEW_DATABASE_NAME = "New_Recipe_Database.db"; // 新しいデータベース名

    private static final String SQL_CREATE_NEW_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY, " + // AUTOINCREMENTを追加
                    COLUMN_NAME_TITLE + " TEXT, " +
                    COLUMN_NAME_SUBTITLE + " TEXT)";


    private static final String SQL_MIGRATE_DATA =
            "INSERT INTO " + TABLE_NAME + " (" +
                    COLUMN_NAME_TITLE + ", " +
                    COLUMN_NAME_SUBTITLE + ") " +
                    "SELECT " + COLUMN_NAME_TITLE + ", " +
                    COLUMN_NAME_SUBTITLE + " FROM " + TABLE_NAME;

    database(Context context) {
        super(context, NEW_DATABASE_NAME, null, NEW_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_CREATE_NEW_ENTRIES); // 新しいテーブルを作成
        db.execSQL(SQL_MIGRATE_DATA); // データを新しいテーブルに移行
        db.execSQL(SQL_DELETE_ENTRIES); // 古いテーブルを削除
    }


    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void updateData(int id, String newData, String newData2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_TITLE, newData);
        contentValues.put(COLUMN_NAME_SUBTITLE, newData2);

        db.update(TABLE_NAME, contentValues, _ID + "= ?", new String[]{String.valueOf(id)});
        db.close();

    }
}

