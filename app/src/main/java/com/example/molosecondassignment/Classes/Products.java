package com.example.molosecondassignment.Classes;

import android.content.Context;
import android.graphics.Bitmap;

public class Products {
    private int id;
    private String productName;
    private String productDescription;
    private String productPrice;
    private String productListPrice;
    private String productRetailPrice;
    private String dateCreated;
    private String dateUpdated;
    private SharedPreferenceManager sharedPreferenceManager;
    private int categoryId;
    private Bitmap productImage;
    private int productQuantity;

    public Products(int id, String productName, String productDescription, String productPrice, String productListPrice,
                    String productRetailPrice, String dateCreated, String dateUpdated, int categoryId, Bitmap productImage) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productListPrice = productListPrice;
        this.productRetailPrice = productRetailPrice;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.categoryId = categoryId;
        this.productImage = productImage;
    }

    public Products(Context context, String productName, String productDescription, String productPrice, String productListPrice,
                    String productRetailPrice, String dateCreated, int categoryId, Bitmap productImage) {
        sharedPreferenceManager = SharedPreferenceManager.getInstance(context);
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productListPrice = productListPrice;
        this.productRetailPrice = productRetailPrice;
        this.dateCreated = dateCreated;
        this.categoryId = categoryId;
        this.productImage = productImage;
    }

    public Products(String productName, String productPrice, String productDescription, Bitmap productImage) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productImage = productImage;
    }

    public Products(String productName, String productPrice, Bitmap productImage) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public Products(int productId, String productName) {
        this.id = productId;
        this.productName = productName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductListPrice() {
        return productListPrice;
    }

    public void setProductListPrice(String productListPrice) {
        this.productListPrice = productListPrice;
    }

    public String getProductRetailPrice() {
        return productRetailPrice;
    }

    public void setProductRetailPrice(String productRetailPrice) {
        this.productRetailPrice = productRetailPrice;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Bitmap getProductImage() {
        return productImage;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public void setProductImage(Bitmap productImage) {
        this.productImage = productImage;
    }

}
