package com.example.reviewsapp.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.reviewsapp.R;
import com.example.reviewsapp.adapters.LoginAdapter;
import com.example.reviewsapp.fragments.LoginFragment;
import com.example.reviewsapp.fragments.RestaurantFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Button start;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    drawerLayout = findViewById(R.id.drawerLayout);
    navigationView = findViewById(R.id.navigationView);
    start = findViewById(R.id.getstarted);
    actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.nav_home:
                    Log.i("MENU_DRAWER_TAG", "Home item is clicked");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.nav_profiles:
                    Log.i("MENU_DRAWER_TAG", "Profiles item is clicked");
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    finish();
                    break;

                case R.id.nav_logout:
                    Log.i("MENU_DRAWER_TAG", "logout item is clicked");
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    break;
            }
            return true;
        }
    });

    start.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Home.this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
            bottomSheetDialog.setCanceledOnTouchOutside(false);

            Button establishments = bottomSheetDialog.findViewById(R.id.establishments);
            Button reviews = bottomSheetDialog.findViewById(R.id.reviews);

            establishments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Establishments.class));
                }
            });

            reviews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Reviews.class));
                }
            });
            bottomSheetDialog.show();
        }
    });
}

}