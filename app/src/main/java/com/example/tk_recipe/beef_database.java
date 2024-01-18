package com.example.tk_recipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class beef_database extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Beefdb";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_SUBTITLE = "recipe";
    public static final String COLUMN_NAME_PICTURE = "beefpicture";
    public static final String _ID = "_id";
    private static final int DATABASE_VERSION = 5;
    private static final int DATABASE_VERSION_NEW = 6;  // 既存のバージョンよりも1つ大きな数
    // バージョンを変更
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME_TITLE + " TEXT, " +
                    COLUMN_NAME_SUBTITLE + " TEXT, " +
                    COLUMN_NAME_PICTURE + " TEXT ) ";  // 列の追加

    public beef_database(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < 6) {
            // バージョン5までのテーブル構造から、新しいCOLUMN_NAME_PICTUREを追加した構造に変更
            sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_NAME_PICTURE + " TEXT");
        }

    }

    public void updateData(int id, String newData, String newData2, String newUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_TITLE, newData);
        contentValues.put(COLUMN_NAME_SUBTITLE, newData2);
        contentValues.put(COLUMN_NAME_PICTURE, newUri);

        db.update(TABLE_NAME, contentValues, _ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Uri getUriById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME_PICTURE}, _ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        Uri uri = null;

        if (cursor != null && cursor.moveToFirst()) {
            String uriString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_PICTURE));
            uri = Uri.parse(uriString);
            cursor.close();
        }

        return uri;
    }
}
