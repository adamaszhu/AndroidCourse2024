package com.adamas.androidcourse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    static final String CREATE_PERSON_SQL =
            "create table Person ("
            + "id integer primary key autoincrement,"
            + "first_name text,"
            + "last_name text)";

    static final String DROP_PERSON_SQL =
            "drop table if exists Person";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PERSON_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_PERSON_SQL);
        onCreate(db);
    }
}
