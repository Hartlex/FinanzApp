package com.example.finanzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.finanzapp.Helpers.Logger;
import com.example.finanzapp.Helpers.Toaster;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class AddEntryActivity extends AppCompatActivity {

    private Spinner _category;
    private EditText _date;
    private Calendar calendar;
    private boolean isExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        Intent intent = getIntent();
        isExpense = intent.getBooleanExtra("isExpense",true);

      
        //Elemente initialisieren
        InitSpinner();
        InitDatePicker();
        InitTabs();
    }

    /**
     * return a view of each object for the category
     *
     * @author Di Seri.F
     * */
    private void InitSpinner(){
        _category = findViewById(R.id.category);
        Spinner spinner = _category;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
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
}