package com.example.finanzapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;

import com.example.finanzapp.Categories.Category;
import com.example.finanzapp.Categories.CategoryType;
import com.example.finanzapp.Helpers.Toaster;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

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
                            entry.getCategoryId()+"','"+
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
    public static EntryContainer GetAllEntries(){

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
        return new EntryContainer(result);
    }
    public static void ClearDatabase() {
        DeleteAll(_helper.TABLE_MONEY_ENTRIES);
        DeleteAll(_helper.TABLE_EXPENSE_CAT);
        DeleteAll(_helper.TABLE_REVENUE_CAT);
        CreateDefaultExpenseCategories();
        CreateDefaultRevenueCategories();

    }
    private static MoneyEntry CreateMoneyEntry(Cursor cur){
        long date = cur.getLong(1);
        double amount = cur.getDouble(2);
        int categoryId = cur.getInt(3);
        Boolean isExpense = cur.getInt(4)==1 ? true:false;
        String comment = cur.getString(5);

        return new MoneyEntry(new Date(date),amount,categoryId,isExpense,comment);
    }
    public static Map<Integer,Category> GetCategories(CategoryType type){
        SQLiteDatabase db = _helper.getReadableDatabase();
        String table = _helper.TABLE_EXPENSE_CAT;
        if(type == CategoryType.REVENUE)
            table = _helper.TABLE_REVENUE_CAT;
        final String cmd =
                "SELECT  * FROM "+table;
        Cursor cur = db.rawQuery(cmd,null);
        int count = cur.getCount();
        Map<Integer,Category> result = new HashMap<>();
        for(int i =0;i<count;i++){
            cur.moveToNext();
            int id = cur.getInt(0);
            String name = cur.getString(1);
            result.put(id,new Category(id,name,type));
        }
        return result;
    }
    public static Category CreateCategory(String name, CategoryType type){
        SQLiteDatabase db = _helper.getWritableDatabase();
        String table = _helper.TABLE_EXPENSE_CAT;
        if(type == CategoryType.REVENUE)
            table = _helper.TABLE_REVENUE_CAT;
        ContentValues values = new ContentValues();
        values.put(_helper.COLUMN_CATNAME, name);
        long id = db.insert(table,null,values);
        return new Category((int)id,name,type);

    }
    public static void DeleteCategory(Category category){
        SQLiteDatabase db = _helper.getWritableDatabase();
        String table = _helper.TABLE_EXPENSE_CAT;
        if(!category.IsExpense())
            table = _helper.TABLE_REVENUE_CAT;
        db.delete(table,_helper.COLUMN_ID+"=?",new String[]{category.GetId()+""});
        db.delete(_helper.TABLE_MONEY_ENTRIES,_helper.COLUMN_CATEGORY+"=?", new String[]{category.GetId()+""});

    }
    private static void DeleteAll(String table){
        String clearDBQuery = "DELETE FROM "+table;
        SQLiteDatabase db = _helper.getWritableDatabase();
        db.execSQL(clearDBQuery);
    }
    private static void CreateDefaultExpenseCategories(){
        CreateCategory("Arzt/Medikamente", CategoryType.EXPENSE);
        CreateCategory("Ausbildung/Studium", CategoryType.EXPENSE);
        CreateCategory("Haushalt", CategoryType.EXPENSE);
        CreateCategory("Haustiere", CategoryType.EXPENSE);
        CreateCategory("Hobby", CategoryType.EXPENSE);
        CreateCategory("Kino/Film/Tv", CategoryType.EXPENSE);
        CreateCategory("Kommunikation", CategoryType.EXPENSE);
        CreateCategory("Kredite/Leasingraten", CategoryType.EXPENSE);
        CreateCategory("Restaurant", CategoryType.EXPENSE);
        CreateCategory("Sonstiges", CategoryType.EXPENSE);
        CreateCategory("Urlaub", CategoryType.EXPENSE);
        CreateCategory("Versicherungen", CategoryType.EXPENSE);
        CreateCategory("Wohnen", CategoryType.EXPENSE);

    }
    private static void CreateDefaultRevenueCategories(){
        CreateCategory("Kindergeld", CategoryType.REVENUE);
        CreateCategory("Lohn/Gehalt", CategoryType.REVENUE);
        CreateCategory("Miete", CategoryType.REVENUE);
        CreateCategory("Rente", CategoryType.REVENUE);
        CreateCategory("Sonstige Einnahmen", CategoryType.REVENUE);
        CreateCategory("Sparen", CategoryType.REVENUE);
        CreateCategory("Taschengeld", CategoryType.REVENUE);



    }
}
