package com.example.finanzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;

public class AddEntryActivity extends AppCompatActivity {

    private Spinner _category;
    private EditText _date;
    private int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        //Elemente initialisieren
        InitSpinner();
        InitDatePicker();

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

        final Calendar calender = Calendar.getInstance();
        _date.setOnClickListener(new View.OnClickListener() {

            /**
             * setting up the DatePickerDialog
             *
             * @author Di Seri.F
             * */
            @Override
            public void onClick(View view) {
                year = calender.get(Calendar.YEAR);
                month = calender.get(Calendar.MONTH);
                day = calender.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEntryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year , int month, int dayOfMonth) {

                        _date.setText(dayOfMonth +  "." + (month+1) + "." + year);

                    }
                }, 2021, month, day);
                datePickerDialog.show();
            }
        });
    }
}