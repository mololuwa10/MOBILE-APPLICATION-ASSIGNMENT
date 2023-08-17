package com.example.molosecondassignment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.molosecondassignment.Classes.AdminPackage.AdminDashboardActivity;
import com.example.molosecondassignment.Classes.AdminPackage.EditProductsActivity;
import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.Classes.Users;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EditProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imagePath;
    private Bitmap imageToStore;
    EditText userNameEdit, userEmailEdit, usersAddressEdit,
            usersPostcodeEdit, usersBioEdit, usersNumberEdit, usersPasswordEdit;
    Button selectUserImageButtonEdit, editUserButton;
    ImageView editUserImageView;
    SharedPreferenceManager sharedPreferenceManager;
    DbClass dbClass;
    ArrayList<Users> usersArrayList;
    Users users;
    CheckBox edit_password_checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userEmailEdit = findViewById(R.id.userEmailEdit);
        userNameEdit = findViewById(R.id.userNameEdit);
        usersAddressEdit = findViewById(R.id.usersAddressEdit);
        usersPostcodeEdit = findViewById(R.id.usersPostcodeEdit);
        usersBioEdit = findViewById(R.id.usersBioEdit);
        usersNumberEdit = findViewById(R.id.usersNumberEdit);
        selectUserImageButtonEdit = findViewById(R.id.selectUserImageButtonEdit);
        editUserImageView = findViewById(R.id.editUserImageView);
        editUserButton = findViewById(R.id.editUserButton);
        usersPasswordEdit = findViewById(R.id.usersPasswordEdit);
        edit_password_checkBox = findViewById(R.id.edit_password_checkBox);

        dbClass = new DbClass(this);
        usersArrayList = dbClass.ReadAll();

        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        int userId = sharedPreferenceManager.getUserId();
        String userPassword = sharedPreferenceManager.getPassword();
        String dateRegistered = sharedPreferenceManager.getDateRegistered();
        String userHobbies = sharedPreferenceManager.getHobbies();
        String userType = sharedPreferenceManager.getUserType();

        userNameEdit.setText(sharedPreferenceManager.getFullName());
        userEmailEdit.setText(sharedPreferenceManager.getEmail());
        usersAddressEdit.setText(sharedPreferenceManager.getAddress());
        usersPostcodeEdit.setText(sharedPreferenceManager.getPostCode());
        usersBioEdit.setText(userHobbies);
        usersNumberEdit.setText(sharedPreferenceManager.getUserNumber());
        usersPasswordEdit.setText(sharedPreferenceManager.getPassword());
        editUserImageView.setImageBitmap(sharedPreferenceManager.getUserImage());

        editUserButton.setOnClickListener(v -> {
            String strUserName = userNameEdit.getText().toString();
            String strUserEmail = userEmailEdit.getText().toString();
            String strUserAddress = usersAddressEdit.getText().toString();
            String strPostCode = usersPostcodeEdit.getText().toString();
            String strUsersNumber = usersNumberEdit.getText().toString();
            String strUsersBio = usersBioEdit.getText().toString();
            String strUserPassword = usersPasswordEdit.getText().toString();

            if(users != null || !strUserName.isEmpty() || !strUserEmail.isEmpty() || !strUserAddress.isEmpty()
                    || !strPostCode.isEmpty() || !strUsersNumber.isEmpty() || !strUsersBio.isEmpty() || !strUserPassword.isEmpty()
                    || editUserImageView != null) {

                Date currentDate = new Date();
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(currentDate);

                sharedPreferenceManager.saveUserDetails(userId, strUserName, strUserEmail, strUserPassword, dateRegistered,
                        dateString, strUsersBio, strPostCode, strUserAddress, userType, strUsersNumber, imageToStore);

                users = new Users(String.valueOf(userId), strUserName, strUserEmail, dateRegistered,
                        dateString, strUserPassword, strUsersBio, strPostCode, strUserAddress, userType, strUsersNumber, imageToStore);
                dbClass.updateUsers(users);
                Toast.makeText(this, "User Updated Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditProfileActivity.this, ViewProfileActivity.class));
            } else {
                Toast.makeText(this, "User not Updated", Toast.LENGTH_SHORT).show();
            }
        });

        selectUserImageButtonEdit.setOnClickListener(v -> {
            selectImage();
        });

        edit_password_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    usersPasswordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    usersPasswordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
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
                editUserImageView.setImageBitmap(imageToStore);
            }
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}