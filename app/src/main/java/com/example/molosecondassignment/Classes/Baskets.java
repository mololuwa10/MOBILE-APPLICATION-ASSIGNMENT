package com.example.molosecondassignment.Classes;

public class Baskets {
    private int basketId;
    private int userId;
    private int productId;
    private String basketQuantity;
    private String basketPrice;
    private String totalPrice;
    private String basketStatus;

    public Baskets(int basketId, int userId, int productId, String basketQuantity, String basketPrice, String totalPrice, String basketStatus) {
        this.basketId = basketId;
        this.userId = userId;
        this.productId = productId;
        this.basketQuantity = basketQuantity;
        this.basketPrice = basketPrice;
        this.totalPrice = totalPrice;
        this.basketStatus = basketStatus;
    }

    public Baskets(int userId, int productId, String basketPrice, String basketQuantity) {
        this.userId = userId;
        this.productId = productId;
        this.basketPrice = basketPrice;
        this.basketQuantity = basketQuantity;
    }

    public Baskets(int userId, int productId, String basketQuantity, String basketId, String basketPrice) {
        this.userId = userId;
        this.productId = productId;
        this.basketQuantity = basketQuantity;
        this.basketId = Integer.parseInt(basketId);
        this.basketPrice = basketPrice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBasketId() {
        return basketId;
    }

    public void setBasketId(int basketId) {
        this.basketId = basketId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getBasketQuantity() {
        return basketQuantity;
    }

    public void setBasketQuantity(String basketQuantity) {
        this.basketQuantity = basketQuantity;
    }

    public String getBasketPrice() {
        return basketPrice;
    }

    public void setBasketPrice(String basketPrice) {
        this.basketPrice = basketPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBasketStatus() {
        return basketStatus;
    }

    public void setBasketStatus(String basketStatus) {
        this.basketStatus = basketStatus;
    }
}
