package com.example.reviewsapp.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.reviewsapp.R;
import com.example.reviewsapp.adapters.LoginAdapter;
import com.example.reviewsapp.fragments.LoginFragment;
import com.example.reviewsapp.fragments.RegisterFragment;
import com.example.reviewsapp.fragments.RestaurantFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{

    private TabLayout tab;
    private ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab = findViewById(R.id.tabLayout);
        pager = findViewById(R.id.viewPager);

        tab.setupWithViewPager(pager);

        LoginAdapter loginAdapter = new LoginAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        loginAdapter.getFragment(new LoginFragment(), "LOGIN");
        loginAdapter.getFragment(new RegisterFragment(), "REGISTER");

        pager.setAdapter(loginAdapter);
    }
}