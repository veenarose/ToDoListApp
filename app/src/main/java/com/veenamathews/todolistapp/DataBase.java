package com.veenamathews.todolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by VEENAROSE on 2016-02-17.
 */
public class DataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SQLiteData.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "todolist";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_STATE = "taskstate";

    // Constructor
    public DataBase (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    // The on create method
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_STATE + " TEXT)"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Insertion method
    public boolean insertList(String title, String state) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_STATE, state);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    // Update Task Method
    public boolean updatePerson(Integer id, String title, String state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_STATE, state);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
        return true;
    }
}
