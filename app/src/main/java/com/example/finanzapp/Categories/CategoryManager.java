package com.example.finanzapp.Categories;

import android.widget.ArrayAdapter;

import com.example.finanzapp.Helpers.Toaster;
import com.example.finanzapp.database.Database;
import com.example.finanzapp.database.MoneyEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryManager {
    private static Map<Integer,Category> _expenseCategories = new HashMap<>();
    private static Map<Integer,Category> _revenueCategories = new HashMap<>();

    public static void Initialize(){
        LoadCategories();
    }

    private static void LoadCategories(){
       _expenseCategories = Database.GetCategories(CategoryType.EXPENSE);
       _revenueCategories = Database.GetCategories(CategoryType.REVENUE);
    }

    /**
     * Retrieve the category by the id
     *
     * @author Di Seri.F
     * */

    public static Category GetCategory(int id){
        if(_expenseCategories.containsKey(id))
            return _expenseCategories.get(id);
        else if(_revenueCategories.containsKey(id))
            return _revenueCategories.get(id);
        return null;
    }

    /**
     * Retrieve the category by the name
     *
     * @author Di Seri.F
     * */

    public static Category GetCategory(String name){
        for (Category cat:_expenseCategories.values())
        {
            if(cat.GetName()==name) return cat;
        }
        for (Category cat:_revenueCategories.values())
        {
            if(cat.GetName()==name) return cat;
        }
        return null;
    }

    /**
     * Add a new the category in the corresponding type
     *
     * @author Di Seri.F
     * */

    public static void AddCategory(Category category){
        if(category.IsExpense())
            _expenseCategories.put(category.GetId(),category);
        else
            _revenueCategories.put(category.GetId(),category);
        // Add Database
    }
    public static void AddCategories(Category[] categories){
        for (Category cat :categories) {AddCategory(cat);}
    }

    /**
     * Remove an existing category in the corresponding type
     *
     * @author Di Seri.F
     * */

    public static void RemoveCategory(Category category){
        if(category.IsExpense())
            _expenseCategories.remove(category);
        else
            _revenueCategories.remove(category);
        // remove Database
    }
    public static ArrayList<String> GetCategoryNames(CategoryType type){
        if(type == CategoryType.EXPENSE)
            return GetArrayAdapter(_expenseCategories);
        else
            return GetArrayAdapter(_revenueCategories);

    }
    private static ArrayList<String> GetArrayAdapter(Map<Integer,Category> categoryMap){
        ArrayList<String> result = new ArrayList<String>();
        for (Category cat:categoryMap.values())
        {
            result.add(cat.GetName());
        }
        return result;
    }
}

