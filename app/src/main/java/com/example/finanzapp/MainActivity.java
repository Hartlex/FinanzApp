package com.example.finanzapp;

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

public class MainActivity extends AppCompatActivity {

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
