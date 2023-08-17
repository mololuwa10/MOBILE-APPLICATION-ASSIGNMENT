package com.example.molosecondassignment.AdminPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.MainActivity;
import com.example.molosecondassignment.ProfileActivity;
import com.example.molosecondassignment.R;
import com.example.molosecondassignment.UserDashboard;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.makeramen.roundedimageview.RoundedImageView;

public class AdminProfileActivity extends AppCompatActivity {
    BottomNavigationView adminProfile_bottom_navigation;
    SharedPreferenceManager sharedPreferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        TextView logOutTextView = findViewById(R.id.logOutTextView);
        ImageView loginBackButton = findViewById(R.id.loginBackButton);
        TextView usersFullName = findViewById(R.id.usersFullName);
        TextView fullNameTextView = findViewById(R.id.FullNameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        Button userButton = findViewById(R.id.userButton);
        RoundedImageView adminUserImage = findViewById(R.id.adminUserImage);
        adminProfile_bottom_navigation = findViewById(R.id.adminProfile_bottom_navigation);

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
        adminUserImage.setImageBitmap(userImage);

        logOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CLEARING USER DETAILS
                SharedPreferenceManager.getInstance(AdminProfileActivity.this).clearUserDetails();

                // TAKE THE USER BACK TO THE USER-DASHBOARD
                Intent intent = new Intent(AdminProfileActivity.this, UserDashboard.class);
                startActivity(intent);
                finish();
            }
        });


        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminProfileActivity.this, ProfileActivity.class));
            }
        });

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        adminProfile_bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.orders_nav) {
                    startActivity(new Intent(AdminProfileActivity.this, AllOrdersActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.admin_categories_nav) {
                    startActivity(new Intent(AdminProfileActivity.this, AdminCategoriesActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.admin_profile_nav) {
                    if (sharedPreferenceManager.getEmail().isEmpty()) {
                        startActivity(new Intent(AdminProfileActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(AdminProfileActivity.this, AdminProfileActivity.class));
                    }
                    return true;
                }
                else if (item.getItemId() == R.id.admin_product_nav) {
                    startActivity(new Intent(AdminProfileActivity.this, AdminDashboardActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.users_nav) {
                    startActivity(new Intent(AdminProfileActivity.this, AllUsersActivity.class));
                }
                return false;
            }
        });
    }
}