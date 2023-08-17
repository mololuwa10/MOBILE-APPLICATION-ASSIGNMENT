package com.example.molosecondassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.molosecondassignment.Classes.SharedPreferenceManager;

public class ViewProfileActivity extends AppCompatActivity {
    ImageView profile_image, viewProfileBackButton;
    TextView nameOfUser, number_textview, email_textview, address_textview, hobbies_textview;
    Button edit_profile_button, logout_button;
    SharedPreferenceManager sharedPreferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        profile_image = findViewById(R.id.profile_image);
        viewProfileBackButton = findViewById(R.id.viewProfileBackButton);
        nameOfUser = findViewById(R.id.nameOfUser);
        number_textview = findViewById(R.id.number_textview);
        email_textview = findViewById(R.id.email_textview);
        address_textview = findViewById(R.id.address_textview);
        hobbies_textview = findViewById(R.id.hobbies_textview);
        edit_profile_button = findViewById(R.id.edit_profile_button);
        logout_button = findViewById(R.id.logout_button);

        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        String fullName = sharedPreferenceManager.getFullName();
        String email = sharedPreferenceManager.getEmail();
        String hobbies = sharedPreferenceManager.getHobbies();
        String postCode = sharedPreferenceManager.getPostCode();
        String address = sharedPreferenceManager.getAddress();
        Bitmap userImage = sharedPreferenceManager.getUserImage();
        String userNumber = sharedPreferenceManager.getUserNumber();

        if(userImage != null) {
            profile_image.setImageBitmap(userImage);
        } else {
            profile_image.setImageBitmap(null);
        }

        nameOfUser.setText(fullName);
        number_textview.setText(userNumber);
        email_textview.setText(email);
        hobbies_textview.setText(hobbies);
        address_textview.setText(address + " " + postCode);

        viewProfileBackButton.setOnClickListener(v -> startActivity(new Intent(ViewProfileActivity.this, ProfileActivity.class)));

        edit_profile_button.setOnClickListener(v -> {
            startActivity(new Intent(ViewProfileActivity.this, EditProfileActivity.class));
        });

        logout_button.setOnClickListener(v -> {
            SharedPreferenceManager.getInstance(ViewProfileActivity.this).clearUserDetails();

            // TAKE THE USER BACK TO THE USER-DASHBOARD
            Intent intent = new Intent(ViewProfileActivity.this, UserDashboard.class);
            startActivity(intent);
            finish();
        });
    }
}