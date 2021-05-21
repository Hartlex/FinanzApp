package com.example.finanzapp.database;

import java.util.Date;

public class MoneyEntry {
    private Date date;
    private double amount;
    private String category;
    private boolean isExpense;
    private String comment;

    public MoneyEntry(Date date,double amount,String category,boolean isExpense,String comment){
        this.date = date;
        this.amount= amount;
        this.category = category;
        this.isExpense = isExpense;
        this.comment = comment;
    }
    public MoneyEntry(Date date,double amount,String category,boolean isExpense){
        this.date = date;
        this.amount= amount;
        this.category = category;
        this.isExpense = isExpense;
        this.comment = "";
    }
    public MoneyEntry(Date date,double amount,String category,String comment){
        this.date = date;
        this.amount= amount;
        this.category = category;
        this.isExpense = true;
        this.comment = comment;
    }
    public MoneyEntry(Date date,double amount,String category){
        this.date = date;
        this.amount= amount;
        this.category = category;
        this.isExpense = true;
        this.comment = "";
    }

    public Date getDate(){return date;}
    public double getAmount(){return amount;}
    public String getCategory(){return category;}
    public boolean isExpense(){return isExpense;}
    public String getComment(){return comment;}

}
