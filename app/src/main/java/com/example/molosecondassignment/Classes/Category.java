package com.example.molosecondassignment.Classes;

import android.content.Context;
import android.graphics.Bitmap;

public class Category {
    private SharedPreferenceManager sharedPreferenceManager;
    private String categoryName;
    private Bitmap categoryImage;
    private int id;

    public Category(String categoryName, Context context, Bitmap categoryImage) {
        this.categoryName = categoryName;
        sharedPreferenceManager = SharedPreferenceManager.getInstance(context);
        this.categoryImage = categoryImage;
    }

    public Category(String categoryName, Bitmap categoryImage, int id) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(Bitmap categoryImage) {
        this.categoryImage = categoryImage;
    }
}
