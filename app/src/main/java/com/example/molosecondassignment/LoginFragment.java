package com.example.molosecondassignment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.molosecondassignment.Classes.DbClass;
import com.example.molosecondassignment.Classes.SharedPreferenceManager;
import com.example.molosecondassignment.Classes.Users;

import java.util.List;

public class LoginFragment extends Fragment  {
    Button fragmentLoginButton;
    TextView signUpTextView;
    EditText edtTextEmail, editTextPassword;
    CheckBox password_checkBox;
    DbClass dbClass;
    List<Users> usersList;
    SharedPreferenceManager sharedPreferenceManager;

    @Nullable
    @Override
    @SuppressLint("CommitPrefEdits")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        ImageView loginBackButton = view.findViewById(R.id.loginBackButton);
        edtTextEmail = view.findViewById(R.id.edtTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        fragmentLoginButton = view.findViewById(R.id.fragmentLoginButton);
        signUpTextView = view.findViewById(R.id.signUpTextView);
        password_checkBox = view.findViewById(R.id.password_checkBox);
        sharedPreferenceManager = SharedPreferenceManager.getInstance(getActivity());

        dbClass = new DbClass(getActivity());
        usersList = dbClass.ReadAll();

        loginBackButton.setOnClickListener(v -> {
            Intent mainActivityIntent = new Intent(getActivity(), MainActivity.class);
            startActivity(mainActivityIntent);
        });

        fragmentLoginButton.setOnClickListener(v -> {
            String userEmail = edtTextEmail.getText().toString();
            String userPassword = editTextPassword.getText().toString();

            if(userEmail.isEmpty() && userPassword.isEmpty()) {
                Toast.makeText(getActivity(), "All Fields are required", Toast.LENGTH_SHORT).show();
                return;
            }
                Users u1 = dbClass.checkUsernamePassword(userEmail, userPassword);
                if(u1 != null) {
                    Intent userDashboardIntent = new Intent(getActivity(), UserDashboard.class);
                    startActivity(userDashboardIntent);

                    sharedPreferenceManager.saveUserDetails(Integer.parseInt(u1.getId()), u1.getFullName(), u1.getEmail(), u1.getPassword(),
                            u1.getDateRegistered(), u1.getDateUpdated(), u1.getHobbies(), u1.getPostCode(),
                            u1.getAddress(), u1.getUserType(), u1.getUserNumber(), u1.getUserImage());

                    Toast.makeText(getActivity(), "Welcome " + u1.getFullName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "User Does Not Exist", Toast.LENGTH_SHORT).show();
                }
            });

        password_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment loginFragment = new LoginFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, loginFragment)
                        .addToBackStack(null) // Add to back stack to enable back navigation
                        .commit();
            }
        });
        return view;
    }

}
