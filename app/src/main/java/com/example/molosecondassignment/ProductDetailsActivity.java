package com.example.molosecondassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.molosecondassignment.Classes.Baskets;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.Orders;
import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.Classes.SelectedProductsManager;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;

import java.util.ArrayList;

public class ProductDetailsActivity extends AppCompatActivity {
    ImageView productDetailsBackButton, productImageView;
    TextView productNameTextView, productPriceTextView, productDescriptionTextView;
    Button addToCartButton;
    SharedPreferenceManager sharedPreferenceManager;
    DbClass dbClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productDetailsBackButton = findViewById(R.id.productDetailsBackButton);
        productImageView = findViewById(R.id.productImageView);
        productNameTextView = findViewById(R.id.productNameTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        productDescriptionTextView = findViewById(R.id.productDescriptionTextView);
        addToCartButton = findViewById(R.id.addToCartButton);

        dbClass = new DbClass(this);

        // SHARED PREFERENCES TO GET THE PRODUCT DETAILS
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        String productName = sharedPreferenceManager.getProductName();
        String productPrice = sharedPreferenceManager.getProductPrice();
        String productDescription = sharedPreferenceManager.getProductDescription();
        Bitmap productImage = sharedPreferenceManager.getProductImageBitmap();
        String productId = sharedPreferenceManager.getProductId();
        int loggedInUser = sharedPreferenceManager.getUserId();

        productNameTextView.setText(productName);
        productPriceTextView.setText("Price: " + productPrice);
        productDescriptionTextView.setText(productDescription);
        productImageView.setImageBitmap(productImage);

        productDetailsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetailsActivity.this, UserDashboard.class));
            }
        });


        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferenceManager.saveProductDetails(loggedInUser, productId, productName, productPrice, productDescription, productImage);

                if(sharedPreferenceManager.getEmail().isEmpty()) {
                    Toast.makeText(ProductDetailsActivity.this, "Please Login to add product to the cart!!", Toast.LENGTH_SHORT).show();
                } else {
                    Baskets baskets = new Baskets(loggedInUser, Integer.parseInt(productId), productPrice, String.valueOf(1));
                    dbClass.addBaskets(baskets);
                }
                Toast.makeText(ProductDetailsActivity.this, "Product Added To Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
}