package com.example.finanzapp;
import android.content.Context;

import androidx.fragment.app.Fragment;
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
public class SettingsFragmentTest {
    private Context appContext;
    @Before
    public void Init(){
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Database db = new Database(appContext);
        CategoryManager.Initialize();
    }
    @Test
    public void CreateNewCategoryTest() {
        String newCatName1 = "Test1";
        CategoryType type1 = CategoryType.EXPENSE;
        CategoryManager.AddCategory(newCatName1,type1);
        Category cat1 = CategoryManager.GetCategory(newCatName1);
        assertNotEquals(cat1,null);

        String newCatName2 = "Test2";
        CategoryType type2 = CategoryType.REVENUE;
        CategoryManager.AddCategory(newCatName2,type2);
        Category cat2 = CategoryManager.GetCategory(newCatName2);
        assertNotEquals(cat2,null);
    }

    @Test
    public void DeleteCategoryTest() {
        String newCatName1 = "Test1";
        CategoryType type1 = CategoryType.EXPENSE;
        CategoryManager.AddCategory(newCatName1,type1);
        Category cat1 = CategoryManager.GetCategory(newCatName1);
        assertNotEquals(cat1,null);

        String newCatName2 = "Test2";
        CategoryType type2 = CategoryType.REVENUE;
        CategoryManager.AddCategory(newCatName2,type2);
        Category cat2 = CategoryManager.GetCategory(newCatName2);
        assertNotEquals(cat2,null);
    }
}
