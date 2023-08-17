package com.example.molosecondassignment.Classes;

import java.util.ArrayList;

public class FilteredProducts {
    private static FilteredProducts instance;
    private ArrayList<Products> filteredProducts;

    private FilteredProducts() {
        filteredProducts = new ArrayList<>();
    }

    public static synchronized FilteredProducts getInstance() {
        if (instance == null) {
            instance = new FilteredProducts();
        }
        return instance;
    }

    public ArrayList<Products> getFilteredProducts() {
        return filteredProducts;
    }

    public void setFilteredProducts(ArrayList<Products> filteredProducts) {
        this.filteredProducts = filteredProducts;
    }
}

