package com.example.greenfoodjava.ui.login;

import android.content.Intent;
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
        setContentView(R.layout.user_registration);
    }

    public void goToEnterpriseRegistration(View v){
        startActivity(new Intent(UserRegistrationActivity.this, EnterpriseRegistrationActivity.class));
    }

    public void loginProcess(View v){
        if (emailHasCorrectFormat() & nameIsComplete()){
        }
    }

    private boolean nameIsComplete() {
        TextView nameField = findViewById(R.id.name);
        String name = nameField.getText().toString();
        if (name.isEmpty()){
            nameField.setError("Name is empty");
            return false;
        }
        return true;
    }

    private boolean emailHasCorrectFormat() {
        TextView emailField = findViewById(R.id.email);
        String email = emailField.getText().toString();
        if(! email.matches("(\\w|-|_)+@\\w+\\.(com|es)")){
            emailField.setError("Invalid email");
            return false;
        }
        return true;
    }
}
