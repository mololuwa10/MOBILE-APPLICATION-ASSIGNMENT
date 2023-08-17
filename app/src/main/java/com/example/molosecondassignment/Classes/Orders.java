package com.example.molosecondassignment.Classes;

import java.util.ArrayList;

public class Orders {
    private int orderId;
    private int userId;
    private ArrayList<Products> products;
    private String price;
    private String totalAmount;
    private String dateOrdered;
    private String orderStatus;
    private String shippingAddress;
    private String quantity;

    public Orders() {
    }

    public Orders(int userId, ArrayList<Products> products, String totalAmount, String dateOrdered, String orderStatus, String shippingAddress) {
        this.userId = userId;
        this.products = products;
        this.totalAmount = totalAmount;
        this.dateOrdered = dateOrdered;
        this.orderStatus = orderStatus;
        this.shippingAddress = shippingAddress;
    }

    public Orders(int orderId, ArrayList<Products> productsList, String orderStatus) {
        this.orderId = orderId;
        this.products = productsList;
        this.orderStatus = orderStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<Products> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Products> products) {
        this.products = products;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(String dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


}
