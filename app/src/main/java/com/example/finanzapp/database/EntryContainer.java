package com.example.finanzapp.database;

import com.example.finanzapp.Categories.Category;
import com.example.finanzapp.Categories.CategoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EntryContainer {
    private Map<Category, ArrayList<MoneyEntry>> _entries = new HashMap<>();

    public EntryContainer(MoneyEntry[] entries){
        for (MoneyEntry entry:entries)
        {
            Category cat = CategoryManager.GetCategory(entry.getCategoryId());
            if(!_entries.containsKey(cat))
                _entries.put(cat,new ArrayList<MoneyEntry>());
            _entries.get(cat).add(entry);

        }
    }
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
