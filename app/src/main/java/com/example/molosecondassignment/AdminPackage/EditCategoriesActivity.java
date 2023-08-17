package com.example.molosecondassignment.AdminPackage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.molosecondassignment.Classes.Category;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.R;

import java.util.ArrayList;
import java.util.Objects;

public class EditCategoriesActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imagePath;
    private Bitmap imageToStore;
    ArrayList<Category> categories;
    ImageView categoryImageView;
    Button selectImageButton, editCategoryButton;
    TextView IdTextView;
    EditText edtTextEditCategory;
    DbClass dbClass;
    Category category;
    SharedPreferenceManager sharedPreferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_categories);

        dbClass = new DbClass(EditCategoriesActivity.this);
        categories = dbClass.ReadAllCategories();

        // SHARED PREFERENCES TO GET THE CATEGORY DETAILS
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        String categoryName = sharedPreferenceManager.getCategoryName();
        Bitmap categoryImage = sharedPreferenceManager.getCategoryImageBitmap();
        String categoryId = sharedPreferenceManager.getCategoryId();

        categoryImageView = findViewById(R.id.categoryImageView);
        selectImageButton = findViewById(R.id.selectImageButton);
        editCategoryButton = findViewById(R.id.editCategoryButton);
        edtTextEditCategory = findViewById(R.id.edtTextEditCategory);
        IdTextView = findViewById(R.id.IdTextView);

        IdTextView.setText(categoryId);
        edtTextEditCategory.setText(categoryName);
        categoryImageView.setImageBitmap(categoryImage);

        editCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strCategoryName = edtTextEditCategory.getText().toString();
                String Id = IdTextView.getText().toString();

                if (category != null && !strCategoryName.isEmpty() || categoryImageView != null || !Id.isEmpty() || imageToStore != null) {
                    // Update the category details
                    sharedPreferenceManager.saveCategoryDetails(strCategoryName, imageToStore, Id);

                    Category updatedCategory = new Category(strCategoryName, imageToStore, Integer.parseInt(Id));
                    dbClass.updateCategories(updatedCategory);

                    Toast.makeText(EditCategoriesActivity.this, "Category Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditCategoriesActivity.this, AdminCategoriesActivity.class));
                } else {
                    sharedPreferenceManager.saveCategoryDetails(strCategoryName, null, Id);
                    Toast.makeText(EditCategoriesActivity.this, "No changes made", Toast.LENGTH_SHORT).show();
                }
            }
        });

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
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