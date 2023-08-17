package com.example.molosecondassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class OrderConfirmationActivity extends AppCompatActivity {
    TextView textViewOrderNumberLabel, textViewOrderDetails, textViewTotalAmountLabel, textViewShippingAddress;
    Button buttonFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        textViewOrderNumberLabel = findViewById(R.id.textViewOrderNumberLabel);
        textViewOrderDetails = findViewById(R.id.textViewOrderDetails);
        textViewTotalAmountLabel = findViewById(R.id.textViewTotalAmountLabel);
        textViewShippingAddress = findViewById(R.id.textViewShippingAddress);
        buttonFinish = findViewById(R.id.buttonFinish);

        // Generate random letters and numbers to form an order Number
        int length = 10;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        for(int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }
        // ----------------------------------------------

        String randomString = sb.toString();

        Intent intent = getIntent();
        String totalAmount = intent.getStringExtra("totalAmount");
        String orderDetails = intent.getStringExtra("orderDetails");
        String shippingAddress = intent.getStringExtra("shippingAddress");

        textViewOrderNumberLabel.setText("Order Number: " + randomString);
        textViewTotalAmountLabel.setText("Total Amount: £" + totalAmount);
//        textViewOrderDetails.setText("Order Details: " + orderDetails);
        textViewShippingAddress.setText(shippingAddress);

        buttonFinish.setOnClickListener(v -> {
            startActivity(new Intent(OrderConfirmationActivity.this, UserDashboard.class));
        });
    }
}