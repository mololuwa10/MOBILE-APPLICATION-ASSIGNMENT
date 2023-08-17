package com.example.molosecondassignment.Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class SharedPreferenceManager {
    private static SharedPreferenceManager instance;
    private SharedPreferences sharedPreferences;
    Context context;

    final String PREF_NAME = "MyAppPreferences";

    final String KEY_FILTERED_PRODUCTS = "filteredProducts";

    private SharedPreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences("userDetails", Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceManager(context);
        }
        return instance;
    }

    public void saveUserDetails(int userId, String fullName, String email, String password, String dateRegistered,
                                String dateUpdated, String hobbies, String postCode,
                                String address, String userType, String userNumber, Bitmap userImage) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", userId);
        editor.putString("fullName", fullName);
        editor.putString("email", email);
        editor.putString("password",password);
        editor.putString("date_registered", dateRegistered);
        editor.putString("date_updated", dateUpdated);
        editor.putString("hobbies", hobbies);
        editor.putString("postcode", postCode);
        editor.putString("address", address);
        editor.putString("userType", userType);
        editor.putString("userNumber", userNumber);
        String imageString = encodeBitmap(userImage);
        editor.putString("userImage", imageString);
        editor.apply();
    }


    public void clearUserDetails() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public int getUserId() {
        return sharedPreferences.getInt("userId", 0);
    }
    public String getFullName() {
        return sharedPreferences.getString("fullName", "");
    }
    public String getEmail() {
        return sharedPreferences.getString("email", "");
    }
    public String getPassword() {
        return sharedPreferences.getString("password", "");
    }
    public String getDateRegistered() {
        return sharedPreferences.getString("date_registered", "");
    }
    public String getDateUpdated() {
        return sharedPreferences.getString("date_updated", "");
    }
    public String getHobbies() {
        return sharedPreferences.getString("hobbies", "");
    }
    public String getAddress() {
        return sharedPreferences.getString("address", "");
    }
    public String getPostCode() {
        return sharedPreferences.getString("postcode", "");
    }
    public String getUserType() {
        return sharedPreferences.getString("userType", "");
    }
    public String getUserNumber() {
        return sharedPreferences.getString("userNumber", "");
    }
    public Bitmap getUserImage() {
        String imageString = sharedPreferences.getString("userImage", "");
        return decodeBitmap(imageString);
    }

    // SHARED PREFERENCES FOR CATEGORIES
    public void saveCategoryName(String categoryName, Bitmap categoryImage) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("categoryName", categoryName);
        String imageString = encodeBitmap(categoryImage);
        editor.putString("categoryImage", imageString);
        editor.apply();
    }
    public void saveCategoryDetails(String categoryName, Bitmap categoryImage, String categoryId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("categoryId", categoryId);
        editor.putString("categoryName", categoryName);
        String imageString = encodeBitmap(categoryImage);
        editor.putString("categoryImage", imageString);
        editor.apply();
    }
    private String encodeBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);

    }
    private Bitmap decodeBitmap(String base64) {
        if (base64 == null || base64.isEmpty()) {
            return null;
        }

        try {
            byte[] byteArray = Base64.decode(base64, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getCategoryId() {
        return sharedPreferences.getString("categoryId", "");
    }
    public Bitmap getCategoryImageBitmap() {
        String imageString = sharedPreferences.getString("categoryImage", "");
        return decodeBitmap(imageString);
    }
    public String getCategoryName() {
        return sharedPreferences.getString("categoryName", "");
    }

    // SHARED PREFERENCES FOR PRODUCTS
    public void saveProductDetails(int userId, String productId, String productName, String productPrice, String productDescription, Bitmap productImage) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("productId", productId);
        editor.putInt("userId", userId);
        editor.putString("productName", productName);
        editor.putString("productPrice", productPrice);
        editor.putString("productDescription", productDescription);
        String imageString = encodeBitmap(productImage);
        editor.putString("productImage", imageString);
        editor.apply();
    }

    public void saveProductDetails(String productName, String productPrice, String productDescription,
                                   Bitmap productImage, String dateCreated, String productListPrice,
                                   String productRetailPrice, String selectedCategory, String productId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("productName", productName);
        editor.putString("productPrice", productPrice);
        editor.putString("productDescription", productDescription);
        String imageString = encodeBitmap(productImage);
        editor.putString("productImage", imageString);
        editor.putString("dateCreated", dateCreated);
        editor.putString("productListPrice", productListPrice);
        editor.putString("productRetailPrice", productRetailPrice);
        editor.putString("selectedCategory", selectedCategory);
        editor.putString("productId", productId);
        editor.apply();
    }

    public void saveProductDetailsWithDateCreated(String productName, String productPrice, String productDescription,
                                   Bitmap productImage, String dateCreated, String productListPrice, String productRetailPrice, String selectedCategory) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("productName", productName);
        editor.putString("productPrice", productPrice);
        editor.putString("productDescription", productDescription);
        String imageString = encodeBitmap(productImage);
        editor.putString("productImage", imageString);
        editor.putString("dateCreated", dateCreated);
        editor.putString("productListPrice", productListPrice);
        editor.putString("productRetailPrice", productRetailPrice);
        editor.putString("selectedCategory", selectedCategory);
        editor.apply();
    }

    public String getProductName() {
        return sharedPreferences.getString("productName", "");
    }
    public String getProductPrice() {
        return sharedPreferences.getString("productPrice", "");
    }
    public String getProductDescription() {
        return sharedPreferences.getString("productDescription", "");
    }
    public Bitmap getProductImageBitmap() {
        String imageString = sharedPreferences.getString("productImage", "");
        return decodeBitmap(imageString);
    }
    public String getProductListPrice() {
        return sharedPreferences.getString("productListPrice", "");
    }
    public String getProductRetailPrice() {
        return sharedPreferences.getString("productRetailPrice", "");
    }
    public String getSelectedCategory() {
        return sharedPreferences.getString("selectedCategory", "");
    }
    public String getProductId() {
        return sharedPreferences.getString("productId", "");
    }

    public String getProductDateCreated() {
        return sharedPreferences.getString("dateCreated", "");
    }

    public void saveOrderDetails(String dateOrdered) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("dateOrdered", dateOrdered);
    }

    public String getDateOrdered() {
        return sharedPreferences.getString("dateOrdered", "");
    }
    public String getOrderId() {
        return sharedPreferences.getString("orderId", "");
    }
}
