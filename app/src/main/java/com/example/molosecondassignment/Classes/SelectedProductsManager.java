package com.example.molosecondassignment.Classes;

import java.util.ArrayList;

public class SelectedProductsManager {
    private static SelectedProductsManager instance;
    private ArrayList<Products> selectedProducts;

    private SelectedProductsManager() {
        selectedProducts = new ArrayList<>();
    }

    public static synchronized SelectedProductsManager getInstance() {
        if (instance == null) {
            instance = new SelectedProductsManager();
        }
        return instance;
    }

    public ArrayList<Products> getSelectedProducts() {
        return selectedProducts;
    }

    public void addSelectedProduct(Products products) {
       for(Products products1 : selectedProducts) {
           if (products1.getId() == products.getId()){
               products1.setProductQuantity(products1.getProductQuantity() + 1);
               return;
           }
           products.setProductQuantity(1);
           selectedProducts.add(products);
       }
    }

    public void clearSelectedProducts() {
        selectedProducts.clear();
    }
}

