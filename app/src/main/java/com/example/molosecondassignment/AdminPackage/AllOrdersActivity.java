package com.example.molosecondassignment.AdminPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.molosecondassignment.Adapters.AdminOrderAdapter;
import com.example.molosecondassignment.Adapters.AdminProductAdapter;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.Orders;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.MainActivity;
import com.example.molosecondassignment.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class AllOrdersActivity extends AppCompatActivity {
    SharedPreferenceManager sharedPreferenceManager;
    BottomNavigationView adminOrders_bottom_navigation;
    RecyclerView orders_list;
    DbClass dbClass;
    ArrayList<Orders> orders;
    AdminOrderAdapter adminOrderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);

        adminOrders_bottom_navigation = findViewById(R.id.adminOrders_bottom_navigation);
        orders_list = findViewById(R.id.orders_list);
        dbClass = new DbClass(this);
        orders = dbClass.getAllOrders();

        orders_list.setLayoutManager(new LinearLayoutManager(AllOrdersActivity.this));
        adminOrderAdapter = new AdminOrderAdapter(AllOrdersActivity.this, orders);
        orders_list.setAdapter(adminOrderAdapter);

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        adminOrders_bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.orders_nav) {
                    startActivity(new Intent(AllOrdersActivity.this, AllOrdersActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.admin_categories_nav) {
                    startActivity(new Intent(AllOrdersActivity.this, AdminCategoriesActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.admin_profile_nav) {
                    if (sharedPreferenceManager.getEmail().isEmpty()) {
                        startActivity(new Intent(AllOrdersActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(AllOrdersActivity.this, AdminProfileActivity.class));
                    }
                    return true;
                }
                else if (item.getItemId() == R.id.admin_product_nav) {
                    startActivity(new Intent(AllOrdersActivity.this, AdminDashboardActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.users_nav) {
                    startActivity(new Intent(AllOrdersActivity.this, AllUsersActivity.class));
                }
                return false;
            }
        });
    }
}