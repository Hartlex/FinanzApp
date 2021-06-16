package com.example.finanzapp;
import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.finanzapp.Categories.Category;
import com.example.finanzapp.Categories.CategoryManager;
import com.example.finanzapp.Categories.CategoryType;
import com.example.finanzapp.database.Database;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class CategoryManagerTest {
    private Context appContext;
    @Before
    public void Init(){
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Database db = new Database(appContext);
        CategoryManager.Initialize();
    }
    @Test
    public void CreateCategoryTest() {
        String name = "Test1";
        CategoryType type = CategoryType.EXPENSE;
        CategoryManager.AddCategory(name,type);
        Category cat = CategoryManager.GetCategory(name);
        assertNotEquals(cat,null);
    }
}
