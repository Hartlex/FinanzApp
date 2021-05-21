package com.example.finanzapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Database {
    private SQLiteDatabase _database;
    private DatabaseHelper _helper;

    public Database(Context context){
        _helper = new DatabaseHelper(context);
    }
}
