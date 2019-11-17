package com.example.greenfoodjava.ui.login;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.greenfoodjava.R;

public class UserRegistrationActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goToEnterpriseRegistration(View v){
        startActivity(new Intent(UserRegistrationActivity.this, EnterpriseRegistrationActivity.class));
    }

    public void loginProcess(View v){
        if (userExist(v)){
            TextView editText = findViewById(R.id.username);
            editText.setText("Email already exist");
        }else {

        }

    }

    private boolean userExist(View v) {
        EditText usernameEditText = findViewById(R.id.username);
        String username = usernameEditText.getText().toString();
        EditText passwordEditText = findViewById(R.id.password);
        String password = passwordEditText.getText().toString();
        return true;
    }
}
