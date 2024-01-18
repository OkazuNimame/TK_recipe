package com.example.tk_recipe;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Pig_database extends SQLiteOpenHelper {

    public static final String TABLE_NAME_PIG = "pigdb";
    public static final String COLUMN_NAME_TITLE_PIG = "pig_title";
    public static final String COLUMN_NAME_SUBTITLE_PIG = "pig_recipe";
    public static final String _ID_PIG = "pig_id";
    public static final String DATABASE_NAME_PIG = "Pig_recipe_database.db";
    public static final int DATABASE_VERSION = 4;
    public static final String PIG_SQL =
            "CREATE TABLE " + TABLE_NAME_PIG + "(" +
                    _ID_PIG + " INTEGER PRIMARY KEY ," +
                    COLUMN_NAME_TITLE_PIG + " TEXT," +
                    COLUMN_NAME_SUBTITLE_PIG + " TEXT)";


    public Pig_database(Context context) {
        super(context, DATABASE_NAME_PIG, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PIG_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PIG);
        onCreate(sqLiteDatabase);
    }

    public void updateData(int id, String newData, String newData2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_TITLE_PIG, newData);
        contentValues.put(COLUMN_NAME_SUBTITLE_PIG, newData2);

        db.update(TABLE_NAME_PIG, contentValues, _ID_PIG + "= ?", new String[]{String.valueOf(id)});
        db.close();

    }
}
