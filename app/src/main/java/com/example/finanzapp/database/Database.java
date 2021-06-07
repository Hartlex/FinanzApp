package com.example.finanzapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;

import com.example.finanzapp.Helpers.Toaster;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Database {
    private static DatabaseHelper _helper;

    public Database(Context context){
        _helper = new DatabaseHelper(context);
    }

    public static boolean AddEntry(MoneyEntry entry){
        SQLiteDatabase db = _helper.getWritableDatabase();
        final String cmd =
                    "INSERT INTO "+_helper.TABLE_MONEY_ENTRIES +
                    "("+_helper.COLUMN_DATE+","+
                            _helper.COLUMN_AMOUNT+","+
                            _helper.COLUMN_CATEGORY+","+
                            _helper.COLUMN_ISEXPENSE+","+
                            _helper.COLUMN_COMMENT+
                            ") " +
                            "VALUES('"+
                            entry.getDate().getTime()+"','"+
                            entry.getAmount()+"','"+
                            entry.getCategory()+"','"+
                            entry.GetIsExpenseAsInt()+"','"+
                            entry.getComment()+
                            "');";
        try {
            db.execSQL(cmd);
            return true;
        }
        catch (SQLException e){
            return false;
        }

    }
    public static MoneyEntry[] GetEntries(Date from,Date to){

        SQLiteDatabase db = _helper.getWritableDatabase();
        long begin = from.getTime();
        long end = to.getTime();

        final String cmd =
                "SELECT  * FROM "+_helper.TABLE_MONEY_ENTRIES+" WHERE " +
                         _helper.COLUMN_DATE+ " BETWEEN ? AND ?";
        Cursor cur = db.rawQuery(cmd,new String[]{begin+"",end+""});
        int count = cur.getCount();
        return null;
    }
    public static MoneyEntry[] GetAllEntries(){

        SQLiteDatabase db = _helper.getReadableDatabase();
        final String cmd =
                "SELECT  * FROM "+_helper.TABLE_MONEY_ENTRIES;
        Cursor cur = db.rawQuery(cmd,null);
        int count = cur.getCount();
        MoneyEntry[] result = new MoneyEntry[count];
        for(int i=0;i<count;i++)
        {
            cur.moveToNext();
            result[i]= CreateMoneyEntry(cur);
        }
        return result;
    }
    public static void ClearDatabase() {
        String clearDBQuery = "DELETE FROM "+_helper.TABLE_MONEY_ENTRIES;
        SQLiteDatabase db = _helper.getWritableDatabase();
        db.execSQL(clearDBQuery);
    }
    private static MoneyEntry CreateMoneyEntry(Cursor cur){
        long date = cur.getLong(1);
        double amount = cur.getDouble(2);
        String category = cur.getString(3);
        Boolean isExpense = cur.getInt(4)==1 ? true:false;
        String comment = cur.getString(5);

        return new MoneyEntry(new Date(date),amount,category,isExpense,comment);
    }
}
