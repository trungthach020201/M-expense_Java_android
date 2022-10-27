package com.comp1786.m_expense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.comp1786.m_expense.Expense.AddExpenseFragment;
import com.comp1786.m_expense.Expense.UpdateExpenseFragment;
import com.comp1786.m_expense.Trip.AddTripFragment;
import com.comp1786.m_expense.Trip.DetailTripFragment;
import com.comp1786.m_expense.Expense.ExpenseFragment;
import com.comp1786.m_expense.Home.HomeFragment;
import com.comp1786.m_expense.Setting.SettingFragment;
import com.comp1786.m_expense.Trip.TripFragment;
import com.comp1786.m_expense.Trip.UpdateTripFragment;
import com.comp1786.m_expense.model.Expenses;
import com.comp1786.m_expense.model.Trip;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity
       implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar1 = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        drawer = findViewById(R.id.draw_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar1,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            replaceFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new HomeFragment()).commit();
                replaceFragment(new HomeFragment());
                break;
            case R.id.nav_trip:
                replaceFragment(new TripFragment());
                break;
            case R.id.nav_expense:
                replaceFragment(new ExpenseFragment());
                break;
            case R.id.nav_setting:
                replaceFragment(new SettingFragment());
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        
        return true;
    }


    public void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container,fragment,null);
        fragmentTransaction.addToBackStack(null).commit();
    }

    public void gotoAddTripFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        AddTripFragment addTripFragment = new AddTripFragment();
        fragmentTransaction.replace(R.id.fragment_container,addTripFragment,null);
        fragmentTransaction.addToBackStack(null).commit();
    }
    public void gotoUpdateTripFragment(Trip trip) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UpdateTripFragment updateTripFragment = new UpdateTripFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("object_trip",trip);
        updateTripFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment_container,updateTripFragment,null);
        fragmentTransaction.addToBackStack(null).commit();
    }

    public void gotoTripDetailfragment(Trip trip){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailTripFragment detailTripFragment = new DetailTripFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("object_trip",trip);
        detailTripFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment_container,detailTripFragment,null);
        fragmentTransaction.addToBackStack(null).commit();
    }

    public void backToTripFragment (){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        TripFragment tripFragment = new TripFragment();

        fragmentTransaction.replace(R.id.fragment_container,tripFragment,null);
        fragmentTransaction.commit();
    }

    public void goToAddExpenseFragment (int tripId){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddExpenseFragment addExpenseFragment = new AddExpenseFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("trip_id",tripId);
        addExpenseFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment_container,addExpenseFragment,null);
        fragmentTransaction.addToBackStack(null).commit();
    }

    public void gotoUpdateExpenseFragment(Expenses expenses) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UpdateExpenseFragment updateExpenseFragment = new UpdateExpenseFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("object_expense", expenses);
        updateExpenseFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment_container,updateExpenseFragment,null);
        fragmentTransaction.addToBackStack(null).commit();
    }

}