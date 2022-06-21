package com.example.reviewsapp.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.reviewsapp.R;
import com.example.reviewsapp.database.DatabaseHelper;
import com.example.reviewsapp.databinding.ActivityEstablishmentsBinding;
import com.example.reviewsapp.fragments.BarFragment;
import com.example.reviewsapp.fragments.CafeFragment;
import com.example.reviewsapp.fragments.RestaurantFragment;

public class Establishments extends AppCompatActivity {

    ActivityEstablishmentsBinding binding;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEstablishmentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new CafeFragment());

        db = new DatabaseHelper(this);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.cafe:
                    replaceFragment(new CafeFragment());
                    break;
                case R.id.restaurant:
                    replaceFragment(new RestaurantFragment());
                    break;
                case R.id.bar:
                    replaceFragment(new BarFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}