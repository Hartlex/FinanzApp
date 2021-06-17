package com.example.finanzapp;
import com.example.finanzapp.Categories.Category;
import com.example.finanzapp.Categories.CategoryType;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
public class CategoryTest {

    @Before
    public void Init(){

    }
    @Test
    public void CreateCategory(){
        String newCatName1 = "NewCat";
        CategoryType type1 = CategoryType.EXPENSE;
        int id1 = 99;
        Category cat1 = new Category(id1,newCatName1,type1);

        assertNotEquals(cat1,null);
        assertEquals(cat1.GetName(),newCatName1);
        assertEquals(cat1.IsExpense(),true);
        assertEquals(cat1.GetId(),id1);

        String newCatName2 = "NewCat";
        CategoryType type2 = CategoryType.REVENUE;
        int id2 = 100;
        Category cat2 = new Category(id2,newCatName2,type2);
        assertNotEquals(cat2,null);
        assertEquals(cat2.GetName(),newCatName2);
        assertEquals(cat2.IsExpense(),false);
        assertEquals(cat2.GetId(),id2);



    }

}
