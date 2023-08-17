package com.example.molosecondassignment.AdminPackage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.molosecondassignment.Classes.Category;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.Products;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EditProductsActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imagePath;
    private Bitmap imageToStore;
    ArrayList<Category> categories;
    ArrayList<Products> productsArrayList;
    EditText edtTextProductName, edtTextProductDescription, editTextProductPrice, edtTextProductListPrice, editTextRetailPrice;
    Button editProductButton, selectImageButton;
    ImageView productImageView;
    SharedPreferenceManager sharedPreferenceManager;
    TextView productIdTextView, dateCreatedTextView;
    DbClass dbClass;
    Products products;
    private int selectedCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);

        edtTextProductName = findViewById(R.id.ProductNameEdit);
        dateCreatedTextView = findViewById(R.id.dateCreatedTextView);
        edtTextProductDescription = findViewById(R.id.ProductDescriptionEdit);
        editTextProductPrice = findViewById(R.id.ProductPriceEdit);
        edtTextProductListPrice = findViewById(R.id.ProductListPriceEdit);
        editTextRetailPrice = findViewById(R.id.RetailPriceEdit);
        editProductButton = findViewById(R.id.editProductButton);
        selectImageButton = findViewById(R.id.selectImageButtonEdit);
        productImageView = findViewById(R.id.editProductImageView);
        productIdTextView = findViewById(R.id.productIdTextView);

        dbClass = new DbClass(EditProductsActivity.this);
        productsArrayList = dbClass.ReadAllProducts();
        categories = dbClass.ReadAllCategories();

        ArrayList<String> categoryNames = new ArrayList<>();
        for (Category category : categories) {
            categoryNames.add(category.getCategoryName());
        }

        Spinner editProductSpinner = findViewById(R.id.editProductSpinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryNames);
        categoryAdapter.setDropDownViewResource(R.layout.categories_drop_down_menu);

        editProductSpinner.setAdapter(categoryAdapter);

        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);

        edtTextProductName.setText(sharedPreferenceManager.getProductName());
        edtTextProductDescription.setText(sharedPreferenceManager.getProductDescription());
        editTextProductPrice.setText(sharedPreferenceManager.getProductPrice());
        productImageView.setImageBitmap(sharedPreferenceManager.getProductImageBitmap());
        edtTextProductListPrice.setText(sharedPreferenceManager.getProductListPrice());
        editTextRetailPrice.setText(sharedPreferenceManager.getProductRetailPrice());
        productIdTextView.setText(sharedPreferenceManager.getProductId());
        dateCreatedTextView.setText(sharedPreferenceManager.getProductDateCreated());

        editProductButton.setOnClickListener(v -> {
            String strProductName = edtTextProductName.getText().toString();
            String strProductDescription = edtTextProductDescription.getText().toString();
            String strProductPrice = editTextProductPrice.getText().toString();
            String strProductListPrice = edtTextProductListPrice.getText().toString();
            String strProductRetailPrice = editTextRetailPrice.getText().toString();
            String productId1 = productIdTextView.getText().toString();
            String dateCreated = dateCreatedTextView.getText().toString();

            if (products != null && !strProductName.isEmpty() || !strProductDescription.isEmpty()
                    || !strProductPrice.isEmpty()|| !strProductListPrice.isEmpty()|| !strProductRetailPrice.isEmpty()
                    || productImageView != null || !productId1.isEmpty() || !dateCreated.isEmpty()) {

                Date currentDate = new Date();
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(currentDate);

                // RETRIEVING THE SELECTED CATEGORY ID FROM THE SPINNER
                String selectedCategoryName = editProductSpinner.getSelectedItem().toString();

                for (Category category : categories) {
                    if (category.getCategoryName().equals(selectedCategoryName)) {
                        selectedCategoryId = category.getId();
                        break;
                    }
                }

                sharedPreferenceManager.saveProductDetails(strProductName, strProductPrice, strProductDescription,
                        imageToStore, dateCreated, strProductListPrice, strProductRetailPrice, Integer.toString(selectedCategoryId) , productId1);

                Products products = new Products(Integer.parseInt(productId1), strProductName, strProductDescription, strProductPrice,
                        strProductListPrice, strProductRetailPrice, dateCreated ,dateString, selectedCategoryId, imageToStore);

                dbClass.updateProducts(products);
                Toast.makeText(EditProductsActivity.this, "Product Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditProductsActivity.this, AdminDashboardActivity.class));
            }
        });

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    // https://stackoverflow.com/questions/39387608/placing-an-picture-in-imageview-selected-from-gallery
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