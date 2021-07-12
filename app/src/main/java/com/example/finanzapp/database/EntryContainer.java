package com.example.finanzapp.database;

import com.example.finanzapp.Categories.Category;
import com.example.finanzapp.Categories.CategoryManager;
import com.example.finanzapp.Categories.CategoryType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Helps to handle multiple instances of MoneyEntries to improve readability in other classes
 * @author Alexander Hartmann
 */
public class EntryContainer {
    private Map<Category, ArrayList<MoneyEntry>> _entries = new HashMap<>();

    /**
     * Creates an instance of this class
     * @param entries all of the MoneyEntries the container should contain
     */
    public EntryContainer(MoneyEntry[] entries){
        for (MoneyEntry entry:entries)
        {
            Category cat = CategoryManager.GetCategory(entry.getCategoryId());
            if(!_entries.containsKey(cat))
                _entries.put(cat,new ArrayList<MoneyEntry>());
            _entries.get(cat).add(entry);

        }
    }

    /**
     * Gets the Money amount of each expense category
     * @return a Map<Category,Double> where Double is the sum amount of all MoneyEntries in that category
     */
    public Map<Category,Double> GetExpenseValues(){
        Map<Category,Double> result= new HashMap<>();
        for (Map.Entry<Category,ArrayList<MoneyEntry>> pair:_entries.entrySet())
        {
            if(!pair.getKey().IsExpense()) continue;
            double value =0;
            for (MoneyEntry entry: pair.getValue())
            {
                if(entry.isExpense())
                    value += entry.getAmount();
            }
            result.put(pair.getKey(),new Double(value));
        }
        return result;
    }
    /**
     * Gets the Money amount of each revenue category
     * @return a Map<Category,Double> where Double is the sum amount of all MoneyEntries in that category
     */
    public Map<Category,Double> GetRevenueValues(){
        Map<Category,Double> result= new HashMap<>();
        for (Map.Entry<Category,ArrayList<MoneyEntry>> pair:_entries.entrySet())
        {
            if(pair.getKey().IsExpense()) continue;
            double value =0;
            for (MoneyEntry entry: pair.getValue())
            {
                    value += entry.getAmount();
            }
            result.put(pair.getKey(),new Double(value));
        }
        return result;
    }

    /**
     * Gets the sum of all expenses and revenues
     * @return a Mapy<CategoryType,Double> where Double is all the money in that category type
     */
    public Map<CategoryType,Double> GetExpRevRatio(){
        Map<CategoryType,Double> result= new HashMap<>();
        double expense =0;
        double revenue =0;
        for (Map.Entry<Category,ArrayList<MoneyEntry>> pair:_entries.entrySet())
        {

            for (MoneyEntry entry: pair.getValue())
            {
                if(entry.isExpense())
                    expense += entry.getAmount();
                else
                    revenue += entry.getAmount();
            }

        }
        result.put(CategoryType.EXPENSE,expense);
        result.put(CategoryType.REVENUE,revenue);
        return result;
    }

    /**
     * Deprecated DONT USE
     * @return
     */
    public Map<Category,Double> GetValues(){
        Map<Category,Double> result= new HashMap<>();
        for (Map.Entry<Category,ArrayList<MoneyEntry>> pair:_entries.entrySet())
        {
            double value =0;
            for (MoneyEntry entry: pair.getValue())
            {
                value += entry.getAmount();
            }
            result.put(pair.getKey(),new Double(value));
        }
        return result;
    }
}
