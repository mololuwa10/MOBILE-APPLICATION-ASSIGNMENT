package com.example.molosecondassignment.AdminPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.molosecondassignment.Adapters.AdminCategoryAdapter;
import com.example.molosecondassignment.Adapters.AdminProductAdapter;
import com.example.molosecondassignment.CartActivity;
import com.example.molosecondassignment.CategoriesActivity;
import com.example.molosecondassignment.Classes.Category;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.MainActivity;
import com.example.molosecondassignment.ProfileActivity;
import com.example.molosecondassignment.R;
import com.example.molosecondassignment.UserDashboard;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class AdminDashboardActivity extends AppCompatActivity {
    SharedPreferenceManager sharedPreferenceManager;
    BottomNavigationView adminProduct_bottom_navigation;
    ImageView addProductButton;
    RecyclerView products_list;
    AdminProductAdapter adminProductAdapter;
    ArrayList<Products> products;
    DbClass dbClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        adminProduct_bottom_navigation = findViewById(R.id.adminProduct_bottom_navigation);
        addProductButton = findViewById(R.id.addProductButton);
        products_list = findViewById(R.id.products_list);

        dbClass = new DbClass(AdminDashboardActivity.this);
        products = dbClass.ReadAllProducts();

        products_list.setLayoutManager(new LinearLayoutManager(AdminDashboardActivity.this));
        adminProductAdapter = new AdminProductAdapter(AdminDashboardActivity.this, products);
        products_list.setAdapter(adminProductAdapter);

        adminProductAdapter.setOnItemClickListener(new AdminProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Products products1 = products.get(position);

                // Delete the corresponding record from the database
                dbClass.deleteProducts(products1);
                products.remove(position);
                Toast.makeText(AdminDashboardActivity.this, "Product Deleted", Toast.LENGTH_SHORT).show();
                adminProductAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onEditItemClick(int position) {
                Products products1 = products.get(position);
                sharedPreferenceManager = SharedPreferenceManager.getInstance(AdminDashboardActivity.this);

                if (products1.getProductImage() != null || products1.getDateCreated() != null) {
                    sharedPreferenceManager.saveProductDetails(products1.getProductName(), products1.getProductPrice(), products1.getProductDescription(),
                            products1.getProductImage(), products1.getDateCreated(), products1.getProductListPrice(), products1.getProductRetailPrice(),
                            Integer.toString(products1.getCategoryId()), Integer.toString(products1.getId()));
                } else {
                    sharedPreferenceManager.saveProductDetails(products1.getProductName(), products1.getProductPrice(), products1.getProductDescription(),
                            null, products1.getDateCreated(), products1.getProductListPrice(), products1.getProductRetailPrice(),
                            Integer.toString(products1.getCategoryId()), Integer.toString(products1.getId()));
                }

                // Start a new activity or perform any other desired action
                Intent intent = new Intent(AdminDashboardActivity.this, EditProductsActivity.class);
                startActivity(intent);
            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, AddProductsActivity.class));
            }
        });

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        adminProduct_bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.orders_nav) {
                    startActivity(new Intent(AdminDashboardActivity.this, AllOrdersActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.admin_categories_nav) {
                    startActivity(new Intent(AdminDashboardActivity.this, AdminCategoriesActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.admin_profile_nav) {
                    if (sharedPreferenceManager.getEmail().isEmpty()) {
                        startActivity(new Intent(AdminDashboardActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(AdminDashboardActivity.this, AdminProfileActivity.class));
                    }
                    return true;
                }
                else if (item.getItemId() == R.id.admin_product_nav) {
                    startActivity(new Intent(AdminDashboardActivity.this, AdminDashboardActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.users_nav) {
                    startActivity(new Intent(AdminDashboardActivity.this, AllUsersActivity.class));
                }
                return false;
            }
        });
    }

}