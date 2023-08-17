package com.example.molosecondassignment.AdminPackage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.molosecondassignment.Classes.Category;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddProductsActivity extends AppCompatActivity {
    ArrayList<Category> categories;
    DbClass dbClass;
    EditText edtTextProductName, edtTextProductDescription, editTextProductPrice, edtTextProductListPrice, editTextRetailPrice;
    Button addProductButton, selectImageButton;
    ImageView productImageView;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imagePath;
    private Bitmap imageToStore;
    // Declare a variable to store the selected category ID
    private int selectedCategoryId;
    SharedPreferenceManager sharedPreferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);

        edtTextProductName = findViewById(R.id.edtTextProductName);
        edtTextProductDescription = findViewById(R.id.edtTextProductDescription);
        editTextProductPrice = findViewById(R.id.editTextProductPrice);
        edtTextProductListPrice = findViewById(R.id.edtTextProductListPrice);
        editTextRetailPrice = findViewById(R.id.editTextRetailPrice);
        addProductButton = findViewById(R.id.addProductButton);
        selectImageButton = findViewById(R.id.selectImageButton);
        productImageView = findViewById(R.id.productImageView);

        dbClass = new DbClass(AddProductsActivity.this);
        categories = dbClass.ReadAllCategories();

        ArrayList<String> categoryNames = new ArrayList<>();
        for (Category category : categories) {
            categoryNames.add(category.getCategoryName());
        }

        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryNames);
        categoryAdapter.setDropDownViewResource(R.layout.categories_drop_down_menu);

        categorySpinner.setAdapter(categoryAdapter);

        addProductButton.setOnClickListener(v -> {
            String strProductName = edtTextProductName.getText().toString();
            String strProductDescription = edtTextProductDescription.getText().toString();
            String strProductPrice = editTextProductPrice.getText().toString();
            String strProductListPrice = edtTextProductListPrice.getText().toString();
            String strProductRetailPrice = editTextRetailPrice.getText().toString();

            if (strProductName.isEmpty() || strProductDescription.isEmpty()
                    || strProductPrice.isEmpty()
                    || strProductListPrice.isEmpty() || strProductRetailPrice.isEmpty()) {
                Toast.makeText(AddProductsActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
            } else if (productImageView.getDrawable() != null && imageToStore != null) {
                // retrieves the current date
                Date currentDate = new Date();
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(currentDate);

                // RETRIEVING THE SELECTED CATEGORY ID FROM THE SPINNER
                String selectedCategoryName = categorySpinner.getSelectedItem().toString();
                for (Category category : categories) {
                    if (category.getCategoryName().equals(selectedCategoryName)) {
                        selectedCategoryId = category.getId();
                        Log.d("selectedCategoryId", String.valueOf(selectedCategoryId));
                        break;
                    }
                }

                sharedPreferenceManager.saveProductDetailsWithDateCreated(strProductName, strProductPrice, strProductDescription, imageToStore, dateString, strProductListPrice,
                        strProductRetailPrice, String.valueOf(selectedCategoryId));

                // Setting the categoryId property to the Products object
                Products products = new Products(AddProductsActivity.this, strProductName, strProductDescription, strProductPrice,
                        strProductListPrice, strProductRetailPrice, dateString, selectedCategoryId, imageToStore);
                dbClass.addProducts(products);

                Toast.makeText(AddProductsActivity.this, "Products Added Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddProductsActivity.this, AdminDashboardActivity.class));
            } else {
                Toast.makeText(AddProductsActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
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
                productImageView.setImageBitmap(imageToStore);
            }
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}