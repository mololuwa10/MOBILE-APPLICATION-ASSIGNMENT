package com.example.molosecondassignment.AdminPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.molosecondassignment.Adapters.AdminCategoryAdapter;
import com.example.molosecondassignment.Classes.Category;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.MainActivity;
import com.example.molosecondassignment.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class AdminCategoriesActivity extends AppCompatActivity {
    BottomNavigationView adminCategories_bottom_navigation;
    RecyclerView categories_list;
    AdminCategoryAdapter adminCategoryAdapter;
    SharedPreferenceManager sharedPreferenceManager;
    ArrayList<Category> categories;
    DbClass dbClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_categories_actvity);

        categories_list = findViewById(R.id.categories_list);
        ImageView addCategoryImgView = findViewById(R.id.addCategoryImgView);
        adminCategories_bottom_navigation = findViewById(R.id.adminCategories_bottom_navigation);

        dbClass = new DbClass(AdminCategoriesActivity.this);
        categories = dbClass.ReadAllCategories();

        categories_list.setLayoutManager(new LinearLayoutManager(AdminCategoriesActivity.this));
        adminCategoryAdapter = new AdminCategoryAdapter(AdminCategoriesActivity.this, categories);
        categories_list.setAdapter(adminCategoryAdapter);

        adminCategoryAdapter.setOnItemClickListener(new AdminCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Category category = categories.get(position);

                // Delete the corresponding record from the database
                dbClass.deleteCategories(category);
                categories.remove(position);
                Toast.makeText(AdminCategoriesActivity.this, "Category Deleted", Toast.LENGTH_SHORT).show();
                adminCategoryAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onEditClick(int position) {
                // Handles the edit button click
                Category category = categories.get(position);

                sharedPreferenceManager = SharedPreferenceManager.getInstance(AdminCategoriesActivity.this);
                    if (category.getCategoryImage() != null) {
                        sharedPreferenceManager.saveCategoryDetails(category.getCategoryName(), category.getCategoryImage(), Integer.toString(category.getId()));
                    } else {
                        sharedPreferenceManager.saveCategoryDetails(category.getCategoryName(), null, Integer.toString(category.getId()));
                    }

                    // Start a new activity or perform any other desired action
                    Intent intent = new Intent(AdminCategoriesActivity.this, EditCategoriesActivity.class);
                    startActivity(intent);
                }
        });


        addCategoryImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoriesActivity.this, AddCategoryActivity.class));
            }
        });

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        adminCategories_bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.orders_nav) {
                    startActivity(new Intent(AdminCategoriesActivity.this, AllOrdersActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.admin_categories_nav) {
                    startActivity(new Intent(AdminCategoriesActivity.this, AdminCategoriesActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.admin_profile_nav) {
                    if (sharedPreferenceManager.getEmail().isEmpty()) {
                        startActivity(new Intent(AdminCategoriesActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(AdminCategoriesActivity.this, AdminProfileActivity.class));
                    }
                    return true;
                }
                else if (item.getItemId() == R.id.admin_product_nav) {
                    startActivity(new Intent(AdminCategoriesActivity.this, AdminDashboardActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.users_nav) {
                    startActivity(new Intent(AdminCategoriesActivity.this, AllUsersActivity.class));
                }
                return false;
            }
        });
    }

}