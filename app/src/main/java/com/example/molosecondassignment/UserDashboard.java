package com.example.molosecondassignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.molosecondassignment.Adapters.CategoryAdapter;
import com.example.molosecondassignment.Adapters.UserProductAdapter;
import com.example.molosecondassignment.Classes.Category;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.Classes.Users;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity {
    BottomNavigationView temp_bottom_navigation;
    RecyclerView categories_list, product_list;
    CategoryAdapter categoryAdapter;
    UserProductAdapter userProductAdapter;
    ArrayList<Category> categories;
    ArrayList<Products> products;
    ArrayList<Users> users;
    DbClass dbClass;
    GridLayoutManager gridLayoutManager;
    SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        temp_bottom_navigation = findViewById(R.id.temp_bottom_navigation);
        product_list = findViewById(R.id.product_list);
        categories_list = findViewById(R.id.categories_list);
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performProductSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performProductSearch(newText);
                return true;
            }
        });

        dbClass = new DbClass(UserDashboard.this);

        categories = dbClass.ReadAllCategories();
        products = dbClass.ReadAllProducts();
        users = dbClass.ReadAll();

        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        int loggedInUserId = sharedPreferenceManager.getUserId();

        gridLayoutManager = new GridLayoutManager(UserDashboard.this, 4, RecyclerView.VERTICAL, false);
        categories_list.setLayoutManager(gridLayoutManager);
        categoryAdapter = new CategoryAdapter(UserDashboard.this, categories);
        categories_list.setAdapter(categoryAdapter);

        gridLayoutManager = new GridLayoutManager(UserDashboard.this, 2, RecyclerView.VERTICAL, false);
        product_list.setLayoutManager(gridLayoutManager);
        userProductAdapter = new UserProductAdapter(UserDashboard.this, products);
        product_list.setAdapter(userProductAdapter);

        userProductAdapter.setOnItemClickListener(new UserProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Products products1 = products.get(position);
                SharedPreferenceManager.getInstance(UserDashboard.this).saveProductDetails(loggedInUserId, String.valueOf(products1.getId()), products1.getProductName(),
                        products1.getProductPrice(), products1.getProductDescription(), products1.getProductImage());

                startActivity(new Intent(UserDashboard.this, ProductDetailsActivity.class));
            }
        });

        setupBottomNavigationView();

    }

    private void performProductSearch(String query) {
        ArrayList<Products> filteredProducts = new ArrayList<>();
        for (Products product : products) {
            if (product.getProductName().toLowerCase().contains(query.toLowerCase())) {
                filteredProducts.add(product);
            }
        }
        userProductAdapter.setProducts(filteredProducts);
    }

    @SuppressLint("ResourceType")
    private void setupBottomNavigationView() {
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);

//        BadgeDrawable badgeDrawable = BadgeDrawable.create(this);
//        badgeDrawable.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
//        badgeDrawable.setBadgeTextColor(ContextCompat.getColor(this, R.color.badge_text_color));
//        badgeDrawable.setMaxCharacterCount(3);
//        badgeDrawable.setNumber(2);

        temp_bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.cart_nav) {
                    startActivity(new Intent(UserDashboard.this, CartActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.categories_nav) {
                    startActivity(new Intent(UserDashboard.this, CategoriesActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.profile_nav) {
                    if (sharedPreferenceManager.getEmail().isEmpty()) {
                        startActivity(new Intent(UserDashboard.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(UserDashboard.this, ProfileActivity.class));
                    }
                    return true;
                } else if (item.getItemId() == R.id.home_nav) {
                    startActivity(new Intent(UserDashboard.this, UserDashboard.class));
                    return true;
                }
                return false;
            }
        });
    }
}