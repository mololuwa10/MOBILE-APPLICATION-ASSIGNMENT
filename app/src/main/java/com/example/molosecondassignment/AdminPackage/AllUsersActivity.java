package com.example.molosecondassignment.AdminPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.molosecondassignment.Adapters.AdminUserAdapter;
import com.example.molosecondassignment.Classes.Category;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.Classes.Users;
import com.example.molosecondassignment.MainActivity;
import com.example.molosecondassignment.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class AllUsersActivity extends AppCompatActivity {
    BottomNavigationView admin_user_bottom_navigation;
    RecyclerView users_list;
    AdminUserAdapter adminUserAdapter;
    SharedPreferenceManager sharedPreferenceManager;
    ArrayList<Users> users;
    DbClass dbClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        admin_user_bottom_navigation = findViewById(R.id.admin_user_bottom_navigation);
        users_list = findViewById(R.id.users_list);
        dbClass = new DbClass(AllUsersActivity.this);

        users = dbClass.ReadAll();

        users_list.setLayoutManager(new LinearLayoutManager(AllUsersActivity.this));
        adminUserAdapter = new AdminUserAdapter(AllUsersActivity.this, users);
        users_list.setAdapter(adminUserAdapter);

        adminUserAdapter.setOnItemClickListener(new AdminUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Users users1 = users.get(position);

                // Delete the corresponding record from the database
                dbClass.deleteUsers(users1);
                users.remove(position);
                Toast.makeText(AllUsersActivity.this, "Users Deleted", Toast.LENGTH_SHORT).show();
                adminUserAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onEditItemClick(int position) {
                Users users1 = users.get(position);
                sharedPreferenceManager = SharedPreferenceManager.getInstance(AllUsersActivity.this);
            }
        });

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        admin_user_bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.orders_nav) {
                    startActivity(new Intent(AllUsersActivity.this, AllOrdersActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.admin_categories_nav) {
                    startActivity(new Intent(AllUsersActivity.this, AdminCategoriesActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.admin_profile_nav) {
                    if (sharedPreferenceManager.getEmail().isEmpty()) {
                        startActivity(new Intent(AllUsersActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(AllUsersActivity.this, AdminProfileActivity.class));
                    }
                    return true;
                }
                else if (item.getItemId() == R.id.admin_product_nav) {
                    startActivity(new Intent(AllUsersActivity.this, AdminDashboardActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.users_nav) {
                    startActivity(new Intent(AllUsersActivity.this, AllUsersActivity.class));
                }
                return false;
            }
        });
    }
}