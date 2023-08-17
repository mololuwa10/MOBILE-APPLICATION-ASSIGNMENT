package com.example.molosecondassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.molosecondassignment.Adapters.AllOrdersAdapter;
import com.example.molosecondassignment.Adapters.UserProductAdapter;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.Orders;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;

import java.util.ArrayList;

public class ViewAllOrdersActivity extends AppCompatActivity {
    RecyclerView allOrderRecyclerView;
    ArrayList<Orders> orders;
    DbClass dbClass;
    GridLayoutManager gridLayoutManager;
    AllOrdersAdapter allOrdersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_orders);

        allOrderRecyclerView = findViewById(R.id.allOrderRecyclerView);
        dbClass = new DbClass(this);
        orders = dbClass.getOrdersByUserId(getCurrentId());

        gridLayoutManager = new GridLayoutManager(ViewAllOrdersActivity.this, 2, RecyclerView.VERTICAL, false);
        allOrderRecyclerView.setLayoutManager(gridLayoutManager);
        allOrdersAdapter = new AllOrdersAdapter(ViewAllOrdersActivity.this, orders);
        allOrderRecyclerView.setAdapter(allOrdersAdapter);
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
}