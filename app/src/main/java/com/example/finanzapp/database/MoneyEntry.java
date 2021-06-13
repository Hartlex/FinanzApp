package com.example.finanzapp.database;

import com.example.finanzapp.Categories.Category;

import java.util.Date;

public class MoneyEntry {
    private Date date;
    private double amount;
    private int categoryId;
    private boolean isExpense;
    private String comment;

    public MoneyEntry(Date date,double amount,int categoryId,boolean isExpense,String comment){
        this.date = date;
        this.amount= amount;
        this.categoryId = categoryId;
        this.isExpense = isExpense;
        this.comment = comment;
    }
    public MoneyEntry(Date date,double amount,int category,boolean isExpense){
        this(date,amount,category,isExpense,"");
    }
    public MoneyEntry(Date date,double amount,int category,String comment){
        this(date,amount,category,true,comment);
    }
    public MoneyEntry(Date date,double amount,int category){
        this(date,amount,category,true,"");
    }

    public Date getDate(){return date;}
    public double getAmount(){return amount;}
    public int getCategoryId(){return categoryId;}
    public boolean isExpense(){return isExpense;}
    public int GetIsExpenseAsInt(){return isExpense? 1:0;}
    public String getComment(){return comment;}

}
