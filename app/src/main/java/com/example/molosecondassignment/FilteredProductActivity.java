package com.example.molosecondassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.molosecondassignment.Adapters.FilteredProductsAdapter;
import com.example.molosecondassignment.Adapters.UserProductAdapter;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.FilteredProducts;
import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;

import java.util.ArrayList;

public class FilteredProductActivity extends AppCompatActivity {
    SharedPreferenceManager sharedPreferenceManager;
    RecyclerView filteredProductRecyclerView;
    GridLayoutManager gridLayoutManager;
    ArrayList<Products> products;
    FilteredProductsAdapter filteredProductsAdapter;
    DbClass dbClass;
    ImageView filteredProductBackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_product);

        filteredProductRecyclerView = findViewById(R.id.filteredProductRecyclerView);
        filteredProductBackButton = findViewById(R.id.filteredProductBackButton);
        dbClass = new DbClass(this);

        products = FilteredProducts.getInstance().getFilteredProducts();

        filteredProductBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FilteredProductActivity.this, CategoriesActivity.class));
            }
        });

        gridLayoutManager = new GridLayoutManager(FilteredProductActivity.this, 2, RecyclerView.VERTICAL, false);
        filteredProductRecyclerView.setLayoutManager(gridLayoutManager);
        filteredProductsAdapter = new FilteredProductsAdapter(FilteredProductActivity.this, products);
        filteredProductRecyclerView.setAdapter(filteredProductsAdapter);
    }
}