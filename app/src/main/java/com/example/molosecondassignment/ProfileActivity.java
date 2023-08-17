package com.example.molosecondassignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.molosecondassignment.Classes.AdminPackage.AdminDashboardActivity;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.Orders;
import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.Classes.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    SharedPreferenceManager sharedPreferenceManager;
    BottomNavigationView temp_bottom_navigation;
    RoundedImageView userRoundedImageView;
    DbClass dbClass;
    Users users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView logOutTextView = findViewById(R.id.logOutTextView);
        ImageView loginBackButton = findViewById(R.id.loginBackButton);
        TextView usersFullName = findViewById(R.id.usersFullName);
        TextView fullNameTextView = findViewById(R.id.FullNameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        Button adminButton = findViewById(R.id.adminButton);
        Button view_profile_button = findViewById(R.id.view_profile_button);
        temp_bottom_navigation = findViewById(R.id.temp_bottom_navigation);
        Button viewAllOrdersButton = findViewById(R.id.viewAllOrdersButton);
        userRoundedImageView = findViewById(R.id.userRoundedImageView);

        // SHARED PREFERENCES TO GET THE USER DETAILS
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        String fullName = sharedPreferenceManager.getFullName();
        String email = sharedPreferenceManager.getEmail();
        String password = sharedPreferenceManager.getPassword();
        String dateRegistered = sharedPreferenceManager.getDateRegistered();
        String dateUpdated = sharedPreferenceManager.getDateUpdated();
        String hobbies = sharedPreferenceManager.getHobbies();
        String postCode = sharedPreferenceManager.getPostCode();
        String address = sharedPreferenceManager.getAddress();
        String userType = sharedPreferenceManager.getUserType();
        Bitmap userImage = sharedPreferenceManager.getUserImage();
        // ------------------------------------------------------------------------------------------

        usersFullName.setText(fullName);
        fullNameTextView.setText(fullName);
        emailTextView.setText(email);
        userRoundedImageView.setImageBitmap(userImage);

        dbClass = new DbClass(this);

        // SETTING THE VISIBILITY OF THE BUTTON IF THE USER IS AN ADMIN
        if(Objects.equals(userType, "0")) {
            adminButton.setVisibility(View.GONE);
        } else if (Objects.equals(userType, "1")) {
            adminButton.setVisibility(View.VISIBLE);
        }

        view_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ViewProfileActivity.class));
            }
        });

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, AdminDashboardActivity.class));
            }
        });

        logOutTextView.setOnClickListener(v -> {
            // CLEARING USER DETAILS
            SharedPreferenceManager.getInstance(ProfileActivity.this).clearUserDetails();

            // TAKE THE USER BACK TO THE USER-DASHBOARD
            Intent intent = new Intent(ProfileActivity.this, UserDashboard.class);
            startActivity(intent);
            finish();
        });

        viewAllOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ViewAllOrdersActivity.class));
            }
        });

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        temp_bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.cart_nav) {
                    startActivity(new Intent(ProfileActivity.this, CartActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.categories_nav) {
                    startActivity(new Intent(ProfileActivity.this, CategoriesActivity.class));
                    return true;
                }
                else if (item.getItemId() == R.id.profile_nav) {
                    if (sharedPreferenceManager.getEmail().isEmpty()) {
                        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                    }
                    return true;
                }
                else if (item.getItemId() == R.id.home_nav) {
                    startActivity(new Intent(ProfileActivity.this, UserDashboard.class));
                    return true;
                }
                return false;
            }
        });
    }

}