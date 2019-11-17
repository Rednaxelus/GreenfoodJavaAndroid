package com.example.greenfoodjava.ui.login;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
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
        if (allFieldsHaveCorrectFormat()){
        }
    }

    private boolean allFieldsHaveCorrectFormat() {
        return emailHasCorrectFormat() & nameIsComplete() & surnameIsComplete()
                & passwordHasCorrectFormat();
    }

    private boolean emailHasCorrectFormat() {
        TextView emailField = findViewById(R.id.email);
        String email = emailField.getText().toString();
        if(!hasCorrectFormat(email)){
            emailField.setError("Invalid email");
            return false;
        }
        return true;
    }

    private boolean hasCorrectFormat(String email) {
        return email.matches("(\\w|-)+@\\w+\\.(com|es)");
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

    private boolean surnameIsComplete() {
        TextView surnameField = findViewById(R.id.surname);
        String surname = surnameField.getText().toString();
        if (surname.isEmpty()){
            surnameField.setError("Surname is empty");
            return false;
        }
        return true;
    }

    private boolean passwordHasCorrectFormat() {
        TextView passwordField = findViewById(R.id.password);
        String password = passwordField.getText().toString();
        if (! password.matches("([A-Za-z]|\\d|.){8,}")){
            passwordField.setError("Invalid password");
            return false;
        }
        return false;
    }
}
