package com.example.finanzapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.finanzapp.Helpers.Logger;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public DatabaseHelper(Context context){
        super(context,"PlaceholderDB",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
