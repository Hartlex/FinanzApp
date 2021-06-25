package com.example.finanzapp;
import android.content.Context;
import android.provider.ContactsContract;

import androidx.fragment.app.Fragment;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.finanzapp.Categories.Category;
import com.example.finanzapp.Categories.CategoryManager;
import com.example.finanzapp.Categories.CategoryType;
import com.example.finanzapp.database.Database;
import com.example.finanzapp.database.EntryContainer;
import com.example.finanzapp.database.MoneyEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private Context appContext;



    @Before
    public void Init(){
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Database db = new Database(appContext);
        Database.ClearDatabase();
        CategoryManager.Initialize();
    }
    @Test
    public void TestAddRemoveEntry(){
        Date date = new Date();
        MoneyEntry entry = new MoneyEntry(date,10,9999,true,"test");
        boolean success = Database.AddEntry(entry);
        assertTrue(success);
        success = Database.RemoveEntry(entry);
        assertTrue(success);

    }
    @Test
    public void TestGetEntryContainer(){
        EntryContainer container = Database.GetEntryContainer();
        assertNotNull(container);
    }
    @Test
    public void TestGetAllEntries(){
        MoneyEntry[] entries = Database.GetAllEntries();
        assertNotNull(entries);
    }
    @Test
    public void TestGetCategories(){
        Map<Integer,Category> cats = Database.GetCategories(CategoryType.EXPENSE);
        assertTrue(cats.size()>0);
        cats = Database.GetCategories(CategoryType.REVENUE);
        assertTrue(cats.size()>0);
    }
    @Test
    public void TestAddRemoveCategory(){
        String testCatName ="testCat";
        Category testCategory1 = Database.CreateCategory(testCatName,CategoryType.EXPENSE);
        assertNotNull(testCategory1);
        Category testCategory2 = Database.CreateCategory(testCatName,CategoryType.REVENUE);
        assertNotNull(testCategory2);
        boolean success = Database.DeleteCategory(testCategory1);
        assertTrue(success);
        success = Database.DeleteCategory(testCategory2);

    }
    @After
    public void Cleanup(){
        Database.ClearDatabase();
    }

}
