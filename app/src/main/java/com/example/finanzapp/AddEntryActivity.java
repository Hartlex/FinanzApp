package com.example.finanzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.finanzapp.Categories.Category;
import com.example.finanzapp.Categories.CategoryManager;
import com.example.finanzapp.Categories.CategoryType;
import com.example.finanzapp.Helpers.Toaster;
import com.example.finanzapp.database.Database;
import com.example.finanzapp.database.MoneyEntry;
import com.google.android.material.tabs.TabLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddEntryActivity extends AppCompatActivity {

    private Spinner _category;
    private EditText _date;
    private Calendar calendar;
    private EditText _amount;
    private EditText _comment;
    private boolean isExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        isExpense = intent.getBooleanExtra("isExpense",true);


        //Elemente initialisieren
        InitConfirmButton();
        _amount = findViewById(R.id.amount);
        _comment = findViewById(R.id.comment);
        InitSpinner();
        InitDatePicker();
        InitTabs();
    }
    /**
     * method for the number format
     *
     * @author Di Seri.F
     * */
    private static String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat ("###,###,##0.00");
        return formatter.format(Double.parseDouble((number)));
    }


    /**
     * return a view of each object for the category
     *
     * @author Di Seri.F
     * */
    private void InitSpinner(){
        _category = findViewById(R.id.category);
        Spinner spinner = _category;
        CategoryType type = CategoryType.EXPENSE;
        if(!isExpense)
            type = CategoryType.REVENUE;
        ArrayList<String> list = CategoryManager.GetCategoryNames(type);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Initializes Date Picker
     *
     * @author Di Seri.F
     * */
    private void InitDatePicker(){
        _date = findViewById(R.id.date);

        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        _date.setText(day +  "." + (month+1) + "." + year);
        _date.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                ShowDatePicker();
            }
        });
    }

    /**
     * display the DatePickerDialog
     *
     * @author Di Seri.F
     * */
    private void ShowDatePicker(){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddEntryActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year , int month, int dayOfMonth) {
                _date.setText(dayOfMonth +  "." + (month+1) + "." + year);
            }
        }, 2021, month, day);

        datePickerDialog.show();
    }

    /**
     * Sets the Tablayout expenseRevenueTab to Expense or Revenue
     * based on isExpense variable from Intent
     *
     * @author Hartmann A.
     * */
    private void InitTabs(){
        final TabLayout tabLayout = findViewById(R.id.expenseRevenueTab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.parent.setBackgroundResource(GetColorIndex(tab));
                isExpense = tab.getPosition() == 1 ? true : false;
                OnTabChange();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        int tabIndex = isExpense ? 1: 0;
        TabLayout.Tab tab = tabLayout.getTabAt(tabIndex);
        tab.select();
        tabLayout.setBackgroundResource(GetColorIndex(tab));
    }

    /**
     * Returns the ColorIndex based on Tab
     *
     * @param tab The tab that determines which ColorIndex is returned.
     * @author Hartmann A.
     * */
    private int GetColorIndex(TabLayout.Tab tab){
        return tab.getPosition()==0 ? R.color.colorrevenue : R.color.colorexpense;
    }

    private void InitConfirmButton(){
        Button btn = findViewById(R.id.btn_confirm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               OnConfirm(v);
            }
        });
    }

    private void OnConfirm(View v){
        Double amount = GetAmount();
        Date date = GetDate();
        Category category = GetCategory();
        String comment = GetComment();
        if(amount==0) return;
        if(!CheckAllValues(amount,date,category)) return;

        MoneyEntry entry = new MoneyEntry(date,amount,category.GetId(),isExpense,comment);
        if(!Database.AddEntry(entry)){
            Toaster.toast("Database Error!", getApplicationContext());
            return;
        }
        finish();

    }
    private Double GetAmount(){
        String amountText = _amount.getText().toString();
       //_amount.setText( formatNumberCurrency(amountText) + "€ ");
        return TryParseAmount(amountText);

    }
    private Double TryParseAmount(String str){
        try
        {
            return Double.parseDouble(str);
        }
        catch(NumberFormatException e){
            Toaster.toast("Bitte geben Sie einen Betrag ein!",getApplicationContext());
        }
        return 0.0;
    }
    private Date GetDate(){
         return new Date(calendar.getTimeInMillis());
    }
    private Category GetCategory()
    {

        String catName = _category.getSelectedItem().toString();
        return CategoryManager.GetCategory(catName);
    }
    private String GetComment(){
        return _comment.getText().toString();
    }
    private Boolean CheckAllValues(Double amount,Date date,Category category){
        if(amount==0) {
            Toaster.toast("Betrag darf nicht 0 sein!",getApplicationContext());
            return false;
        }
        if(date==null){
            Toaster.toast("Ungültiges Datum!",getApplicationContext());
            return false;
        }
        if(category==null){
            Toaster.toast("Ungültige Kategorie!",getApplicationContext());
            return false;
        }
        return true;
    }
    private void OnTabChange(){
        Spinner spinner = _category;
        CategoryType type = CategoryType.EXPENSE;
        if(!isExpense)
            type = CategoryType.REVENUE;
        ArrayList<String> list = CategoryManager.GetCategoryNames(type);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }



}