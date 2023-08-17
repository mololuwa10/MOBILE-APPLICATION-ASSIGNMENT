package com.example.molosecondassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.molosecondassignment.Adapters.OrderAdapter;
import com.example.molosecondassignment.Classes.Baskets;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.Orders;
import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderActivity extends AppCompatActivity {
    RecyclerView cartProductList;
    Button buyNowButton;
    DbClass dbClass;
    ArrayList<Baskets> baskets;
    OrderAdapter orderAdapter;
    TextView cancelTextView, orderUserNameAddress, orderBasketQuantity, orderTotalProduct;
    SharedPreferenceManager sharedPreferenceManager;
    ArrayList<Orders> ordersArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        cartProductList = findViewById(R.id.cartProductList);
        buyNowButton = findViewById(R.id.buyNowButton);
        cancelTextView = findViewById(R.id.cancelTextView);
        orderUserNameAddress = findViewById(R.id.orderUserNameAddress);
        orderBasketQuantity = findViewById(R.id.orderBasketQuantity);
        orderTotalProduct = findViewById(R.id.orderTotalProduct);

        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);

        String userName = sharedPreferenceManager.getFullName();
        String userAddress = sharedPreferenceManager.getAddress();

        dbClass = new DbClass(this);
        baskets = dbClass.readBasketsByUserId(getCurrentId());

        orderBasketQuantity.setText(String.valueOf(baskets.size()));
        orderUserNameAddress.setText(userName + ", " + userAddress);

        double totalPrice = calculateTotalPrice(baskets);
        orderTotalProduct.setText("Total: £" + totalPrice);

        Date currentDate = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentDate);

        orderAdapter = new OrderAdapter(this, baskets);
        cartProductList.setAdapter(orderAdapter);
        cartProductList.setLayoutManager(new LinearLayoutManager(this));

        cancelTextView.setOnClickListener(v -> {
            startActivity(new Intent(OrderActivity.this, CartActivity.class));
        });

        buyNowButton.setOnClickListener(v -> {
            // Getting the rows in the baskets table associated with the UserID
            ArrayList<Baskets> baskets1 = dbClass.readBasketsByUserId(getCurrentId());

            // productIds arraylist to store the product id
            ArrayList<Integer> productIds = new ArrayList<>();

            // getting the product ids from the baskets table
            for(Baskets baskets2: baskets1) {
                int productId = baskets2.getProductId();
                productIds.add(productId);
            }

            // Used for storing the product Objects
            ArrayList<Products> products = new ArrayList<>();
            for (int productId : productIds) {
                Products product = dbClass.getProductById(productId);
                if (product != null) {
                    products.add(product);
                }
            }

            Orders orders = new Orders();
            orders.setUserId(getCurrentId());
            orders.setTotalAmount(String.valueOf(totalPrice));
            orders.setDateOrdered(dateString);
            orders.setOrderStatus("Pending");
            orders.setShippingAddress(userAddress);
            orders.setProducts(products);

            boolean success = dbClass.addOrders(orders);

            if (success) {
                long orderId = dbClass.getGeneratedOrderId();

                Log.d("OrderActivity", String.valueOf(orderId));

                ArrayList<Products> productsArrayList = orders.getProducts();
                dbClass.addOrderProducts((int) orderId, productsArrayList);

                ArrayList<Products> productList = orders.getProducts();
                for (Products product : productList) {
                    int productId = product.getId();
                    dbClass.deleteProductFromBasket(getCurrentId(), productId);
                }
                Toast.makeText(this, "Order Successful!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderActivity.this, OrderConfirmationActivity.class);
                intent.putExtra("totalAmount", String.valueOf(totalPrice));
                intent.putExtra("shippingAddress", userAddress);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Failed to place order. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
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
}