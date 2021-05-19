package com.example.finanzapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn_revenue, btn_expense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView bottomNavView = findViewById(R.id.navigationView);
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                SelectFragment(menuItem);
                return true;
            }
        });

        btn_revenue = (Button) findViewById(R.id.btn_revenue);
        btn_expense = (Button) findViewById(R.id.btn_expense);

        /**  btn_revenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, AddEntryActivity.class);
                startActivity(intent);

            }
        });
        btn_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent (MainActivity.this, AddEntryActivity.class);
                startActivity(intent1);
            }
        });  */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SelectFragment(item);
        return super.onOptionsItemSelected(item);
    }
    private void SelectFragment(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.action_main:
                ChangeFragment(MainFragment.newInstance("",""));
                return;
            case R.id.action_transactions:
                ChangeFragment(TransactionFragment.newInstance("",""));
                return;
            case R.id.action_settings:
                ChangeFragment(SettingsFragment.newInstance("",""));
                return;
        }
    }
    private void ChangeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,fragment, null)
                .setReorderingAllowed(true)
                .addToBackStack("name") // name can be null
                .commit();
    }
}
