package com.example.finanzapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.finanzapp.Helpers.Logger;

import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "FinanceApp.db";
    private static final int DB_VERSION = 4;

    public static final String TABLE_MONEY_ENTRIES = "money_entries";
    public static final String TABLE_EXPENSE_CAT = "expense_categories";
    public static final String TABLE_REVENUE_CAT = "revenue_categories";


    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public final static String COLUMN_AMOUNT = "amount";
    public final static String COLUMN_CATEGORY = "category";
    public final static String COLUMN_ISEXPENSE= "isExpense";
    public final static String COLUMN_COMMENT = "comment";

    public final static String COLUMN_CATNAME = "catName";
    DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);

        Logger.log("Datenbank wurde erstellt!",this.getClass());
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            CreateTable(db);
            CreateExpenseTable(db);
            CreateRevenueTable(db);
            Logger.log("Tabelle wurde erstellt!",this.getClass());
        }
        catch (Exception ex) {
            Logger.log("Fehler beim erstellen der Tabelle!",this.getClass());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    private void CreateTable(SQLiteDatabase db){
        final String SQL_CREATE =
                "CREATE TABLE " + TABLE_MONEY_ENTRIES +
                        "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_DATE + " INTEGER NOT NULL, " +
                        COLUMN_AMOUNT + " REAL NOT NULL, " +
                        COLUMN_CATEGORY + " INTEGER NOT NULL, " +
                        COLUMN_ISEXPENSE + "  INTEGER NOT NULL, " +
                        COLUMN_COMMENT + " TEXT );";
        db.execSQL(SQL_CREATE);
    }
    private void CreateExpenseTable(SQLiteDatabase db){
        final String SQL_CREATE =
                "CREATE TABLE "+ TABLE_EXPENSE_CAT+
                "("+ COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_CATNAME +" TEXT NOT NULL);";
        db.execSQL(SQL_CREATE);
    }
    private void CreateRevenueTable(SQLiteDatabase db){
        final String SQL_CREATE =
                "CREATE TABLE "+ TABLE_REVENUE_CAT+
                        "("+ COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        COLUMN_CATNAME +" TEXT NOT NULL);";
        db.execSQL(SQL_CREATE);
    }

}
