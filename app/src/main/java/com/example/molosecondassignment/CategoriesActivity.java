package com.example.molosecondassignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.molosecondassignment.Adapters.UserCategoryAdapter;
import com.example.molosecondassignment.Adapters.UserProductAdapter;
import com.example.molosecondassignment.Classes.Category;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.FilteredProducts;
import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {
    RecyclerView userCategories_list;
    BottomNavigationView userCategories_bottom_navigation;
    UserCategoryAdapter userCategoryAdapter;
    ArrayList<Category> categories;
    ArrayList<Products> products;
    DbClass dbClass;
    GridLayoutManager gridLayoutManager;
    SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        userCategories_list = findViewById(R.id.userCategories_list);
        userCategories_bottom_navigation = findViewById(R.id.userCategories_bottom_navigation);
        dbClass = new DbClass(this);

        categories = dbClass.ReadAllCategories();
        products = dbClass.ReadAllProducts();

        gridLayoutManager = new GridLayoutManager(CategoriesActivity.this, 2, RecyclerView.VERTICAL, false);
        userCategories_list.setLayoutManager(gridLayoutManager);
        userCategoryAdapter = new UserCategoryAdapter(CategoriesActivity.this, categories);
        userCategories_list.setAdapter(userCategoryAdapter);

        userCategoryAdapter.setOnItemClickListener(new UserCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Category selectedCategory = categories.get(position);

                // Filter the products based on the selected category
                ArrayList<Products> filteredProducts = new ArrayList<>();
                for (Products product : products) {
                    if (product.getCategoryId() == selectedCategory.getId()) {
                        filteredProducts.add(product);

                        FilteredProducts.getInstance().setFilteredProducts(filteredProducts);

                        startActivity(new Intent(CategoriesActivity.this, FilteredProductActivity.class));
                        Log.d("FilteredProducts", "Product Name: " + product.getProductName());
                        Log.d("FilteredProducts", "Product Description: " + product.getProductDescription());
                    }
                }
            }
        });

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);

//        BadgeDrawable badgeDrawable = BadgeDrawable.create(this);
//        badgeDrawable.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
//        badgeDrawable.setBadgeTextColor(ContextCompat.getColor(this, R.color.badge_text_color));
//        badgeDrawable.setMaxCharacterCount(3);
//        badgeDrawable.setNumber(2);

        userCategories_bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.cart_nav) {
                    startActivity(new Intent(CategoriesActivity.this, CartActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.categories_nav) {
                    startActivity(new Intent(CategoriesActivity.this, CategoriesActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.profile_nav) {
                    if (sharedPreferenceManager.getEmail().isEmpty()) {
                        startActivity(new Intent(CategoriesActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(CategoriesActivity.this, ProfileActivity.class));
                    }
                    return true;
                } else if (item.getItemId() == R.id.home_nav) {
                    startActivity(new Intent(CategoriesActivity.this, UserDashboard.class));
                    return true;
                }
                return false;
            }
        });
    }

}