package com.example.molosecondassignment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.Users;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RegisterFragment extends Fragment {
    EditText edtTextFullName, edtTextEmail, editTextAddress, editTextPostcode, editTextPassword;
    Button signUpButton;
    TextView loginTextView;
    DbClass dbClass;

    @Nullable
    @Override
    @SuppressLint("CommitPrefEdits")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_fragment, container, false);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("userDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ImageView signUpBackButton = view.findViewById(R.id.signUpBackButton);
        edtTextFullName = view.findViewById(R.id.edtTextFullName);
        edtTextEmail = view.findViewById(R.id.edtTextEmail);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        editTextPostcode = view.findViewById(R.id.editTextPostcode);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        signUpButton = view.findViewById(R.id.signUpButton);
        loginTextView = view.findViewById(R.id.loginTextView);

        dbClass = new DbClass(getActivity());

        signUpBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivityIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(mainActivityIntent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userFullName = edtTextFullName.getText().toString().trim();
                String userEmail = edtTextEmail.getText().toString().trim();
                String userAddress = editTextAddress.getText().toString().trim();
                String userPostcode = editTextPostcode.getText().toString().toUpperCase();
                String userPassword = editTextPassword.getText().toString();

                if(userFullName.isEmpty() && userEmail.isEmpty() && userAddress.isEmpty() && userPostcode.isEmpty() && userPassword.isEmpty()) {
                    Toast.makeText(getActivity(), "Empty Fields! Make sure you fill all fields", Toast.LENGTH_SHORT).show();
                }
                Date currentDate = new Date();
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(currentDate);

                Users users = new Users(userFullName, userEmail, dateString, userPassword, userPostcode, userAddress);
                dbClass.addUsers(users);

                startActivity(new Intent(getActivity(), UserDashboard.class));

                editor.putString("fullName", users.getFullName());
                editor.putString("email", users.getEmail());
                editor.putString("password", users.getPassword());
                editor.putString("date_registered", users.getDateRegistered());
                editor.putString("date_updated", users.getDateUpdated());
                editor.putString("hobbies", users.getHobbies());
                editor.putString("postcode", users.getPostCode());
                editor.putString("address", users.getAddress());
                editor.putString("userType", users.getUserType());
                editor.apply();

                Toast.makeText(getActivity(), "User Successfully Registered", Toast.LENGTH_SHORT).show();
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click", "TextView clicked");
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new LoginFragment())
                    .addToBackStack(null)
                    .commit();
            }
        });
        return view;
    }
}
