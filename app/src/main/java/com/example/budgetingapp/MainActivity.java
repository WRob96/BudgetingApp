package com.example.budgetingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.Toast;

import com.example.budgetingapp.databinding.ActivityMainBinding;
import com.example.budgetingapp.helpers.DatabaseHelper;
import com.example.budgetingapp.models.MonthlyBudget;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.YearMonth;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public MonthlyBudget currentBudget;
    public DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        db = new DatabaseHelper(this);
        setContentView(binding.getRoot());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView navView = findViewById(R.id.bottom_navigation_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.detailsFragment, R.id.viewAllFragment, R.id.inputFieldsFragment)
                .build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        currentBudget = new MonthlyBudget(YearMonth.now());
    }

}