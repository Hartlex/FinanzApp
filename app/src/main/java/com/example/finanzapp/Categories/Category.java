package com.example.finanzapp.Categories;

/**
 * Define the parameter for the category
 *
 * @author Di Seri.F
 * */

public class Category {
    private int _id;
    private String _name;
    private CategoryType _type;

    public Category(int id,String name,CategoryType type){
        _id =id;
        _name = name;
        _type = type;
    }


    public int GetId(){return _id;}
    public String GetName(){return _name;}
    public boolean IsExpense(){
        return _type==CategoryType.EXPENSE;
    }
}

