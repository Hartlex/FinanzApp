package com.example.finanzapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.finanzapp.Categories.CategoryManager;
import com.example.finanzapp.database.Database;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.ContactsContract;
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
        Database db = new Database(getApplicationContext());
        Database.ClearDatabase();
        CategoryManager.Initialize();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SelectFragment(item);
        BottomNavigationView bottomNavView = findViewById(R.id.navigationView);
        bottomNavView.setSelectedItemId(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    /**
     * Selects a Fragment based on a menuItem.
     * Then calls ChangeFragment to actually change the fragment.
     *
     * @author Hartmann A.
     * @param item The menu item used to select the Fragment
     * */
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

    /**
     * Changes the Content of the R.id.fragmentContainer
     *
     * @author Hartmann.A
     * @param fragment The new Fragment that will be displayed.
     * */
    private void ChangeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,fragment, null)
                .setReorderingAllowed(true)
                .addToBackStack("name") // name can be null
                .commit();
    }
}
