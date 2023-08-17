package com.example.molosecondassignment.AdminPackage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.molosecondassignment.Classes.Category;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.R;

public class AddCategoryActivity extends AppCompatActivity {
    EditText edtTextAddCategory;
    Button addCategoryButton, selectImageButton;
    DbClass dbClass;
    ImageView categoryImageView;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imagePath;
    private Bitmap imageToStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        ImageView addCategoriesBackButton = findViewById(R.id.addCategoriesBackButton);
        edtTextAddCategory = findViewById(R.id.edtTextAddCategory);
        addCategoryButton = findViewById(R.id.addCategoryButton);
        selectImageButton = findViewById(R.id.selectImageButton);
        categoryImageView = findViewById(R.id.categoryImageView);
        SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(getApplicationContext());
        dbClass = new DbClass(this);

        addCategoriesBackButton.setOnClickListener(v -> startActivity(new Intent(AddCategoryActivity.this, AdminCategoriesActivity.class)));

        addCategoryButton.setOnClickListener(v -> {
            String strCategory = edtTextAddCategory.getText().toString();

            if (strCategory.isEmpty()) {
                Toast.makeText(AddCategoryActivity.this, "Field Is Empty", Toast.LENGTH_SHORT).show();
            } else if(categoryImageView.getDrawable() != null && imageToStore != null) {
                // Saving category name to shared preferences
                sharedPreferenceManager.saveCategoryName(strCategory, imageToStore);

                Category category = new Category(strCategory, AddCategoryActivity.this, imageToStore);
                dbClass.addCategories(category);

                Toast.makeText(AddCategoryActivity.this, "Category Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddCategoryActivity.this, AdminCategoriesActivity.class));
            }
        });

        selectImageButton.setOnClickListener(v -> selectImage());
    }

    private void selectImage() {
        try{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imagePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                categoryImageView.setImageBitmap(imageToStore);
            }
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}