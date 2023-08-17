package com.example.molosecondassignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.molosecondassignment.Adapters.CartProductAdapter;
import com.example.molosecondassignment.Adapters.UserProductAdapter;
import com.example.molosecondassignment.Classes.AdminPackage.AdminDashboardActivity;
import com.example.molosecondassignment.Classes.Baskets;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.Classes.SelectedProductsManager;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    CartProductAdapter cartProductAdapter;
    RecyclerView cart_product_list;
    ArrayList<Baskets> baskets;
    DbClass dbClass;
    Baskets basket;
    TextView totalProductTextView;
    Button checkOutButton;
    BottomNavigationView cart_bottom_navigation;
    SharedPreferenceManager sharedPreferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cart_product_list = findViewById(R.id.cart_product_list);
        totalProductTextView = findViewById(R.id.totalProductTextView);
        checkOutButton = findViewById(R.id.checkOutButton);
        cart_bottom_navigation = findViewById(R.id.cart_bottom_navigation);

        dbClass = new DbClass(this);
        baskets = dbClass.readBasketsByUserId(getCurrentId());

        cartProductAdapter = new CartProductAdapter(this, baskets);
        cart_product_list.setAdapter(cartProductAdapter);
        cart_product_list.setLayoutManager(new LinearLayoutManager(this));

        cartProductAdapter.setOnItemClickListener(new CartProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }

            @Override
            public void onAddItemClick(int position) {
                updateTotalPrice();
            }

            @Override
            public void onSubtractClick(int position) {
                updateTotalPrice();
            }

            @Override
            public void onDeleteClick(int position) {
                Baskets baskets1 = baskets.get(position);

                // REMOVE PRODUCT FROM CART
                dbClass.deleteProductsFromCart(baskets1);
                baskets.remove(position);
                Toast.makeText(CartActivity.this, "Product Removed", Toast.LENGTH_SHORT).show();
                cartProductAdapter.notifyItemRemoved(position);

                updateTotalPrice();
            }
        });

        checkOutButton.setOnClickListener(v -> {
            if(baskets.isEmpty()) {
                Toast.makeText(this, "Please Select A Product Before Checking Out!!", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(CartActivity.this, OrderActivity.class));
            }
        });
        updateTotalPrice();

        setupBottomNavigationView();
    }

    public void updateTotalPrice() {
        double totalPrice = calculateTotalPrice(baskets);
        totalProductTextView.setText("£" + totalPrice);
    }

    private double calculateTotalPrice(ArrayList<Baskets> baskets) {
        double totalPrice = 0.0;

        for(Baskets baskets1: baskets) {
            if(baskets1.getUserId() == getCurrentId()) {
                int productId = baskets1.getProductId();
                Products products = dbClass.getProductById(productId);

                String priceString = products.getProductPrice().replaceAll("[^\\d.]", "");
                double productPrice = Double.parseDouble(priceString);
                int basketQuantity = Integer.parseInt(baskets1.getBasketQuantity());

                totalPrice += productPrice * basketQuantity;
            }
        }
        return totalPrice;
    }

    private int getCurrentId() {
        SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        int userId = sharedPreferenceManager.getUserId();

        try {
            return userId;
        } catch(NumberFormatException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private void setupBottomNavigationView(){
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        cart_bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.cart_nav) {
                    startActivity(new Intent(CartActivity.this, CartActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.categories_nav) {
                    startActivity(new Intent(CartActivity.this, CategoriesActivity.class));
                    return true;
                }
                else if (item.getItemId() == R.id.profile_nav) {
                    if (sharedPreferenceManager.getEmail().isEmpty()) {
                        startActivity(new Intent(CartActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(CartActivity.this, ProfileActivity.class));
                    }
                    return true;
                }
                else if (item.getItemId() == R.id.home_nav) {
                    startActivity(new Intent(CartActivity.this, UserDashboard.class));
                    return true;
                }
                return false;
            }
        });
    }

}