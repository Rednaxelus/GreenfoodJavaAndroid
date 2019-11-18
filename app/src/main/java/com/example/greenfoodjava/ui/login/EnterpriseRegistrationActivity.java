package com.example.greenfoodjava.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.greenfoodjava.R;
import com.example.greenfoodjava.database.EnterpriseTable;

import java.util.regex.Pattern;

public class EnterpriseRegistrationActivity extends Activity {
    private boolean isSwitchChecked;
    private boolean fieldError = false;
    private EnterpriseTable dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterprise_registration);
        initSwitchListener();
        dbHelper = new EnterpriseTable(this);
    }

    private void initSwitchListener() {
        Switch s = findViewById(R.id.switch1);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSwitchChecked = isChecked;
            }
        });
        isSwitchChecked = false;
    }

    public void register(View view) {
        fieldError = false;
        checkEmailField();
        checkPassField();
        checkNifField();
        checkDescriptionField();
        checkPhoneField();
        checkAddressField();
        if (!fieldError) {
            createEnterprise();
        }
    }

    private void checkAddressField() {
        EditText addressText = findViewById(R.id.address);
        String address = addressText.getText().toString();
        if (address.length() < 5 || address.length() > 25) {
            addressText.setError("Address doesn't exists");
            fieldError = true;
        }
    }

    private void checkPhoneField() {
        EditText phoneText = findViewById(R.id.phone);
        String phone = phoneText.getText().toString();
        if (phone.length() != 9) {
            phoneText.setError("Phone doesn't exists");
            fieldError = true;
        } else if (!Pattern.matches("(\\d{9})", phone)) {
            phoneText.setError("Phone doesn't exists");
            fieldError = true;
        }
    }

    private void checkDescriptionField() {
        EditText descText = findViewById(R.id.description);
        if (descText.getText().toString().length() > 75) {
            descText.setError("Description too long");
            fieldError = true;
        }
    }

    private void checkNifField() {
        EditText nifText = findViewById(R.id.nif);
        String nif = nifText.getText().toString();
        if (nif.length() != 9) {
            nifText.setError("Wrong nif");
            fieldError = true;
        } else if (!Pattern.matches("(\\d{8}[TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])", nif)) {
            nifText.setError("Wrong nif");
            fieldError = true;
        }
    }

    private void checkPassField() {
        EditText passText = findViewById(R.id.password);
        if (passText.getText().toString().length() <= 6) {
            passText.setError("Password too short");
            fieldError = true;
        }
        if (passText.getText().toString().length() > 50) {
            passText.setError("Password too long");
            fieldError = true;
        }
    }

    private void checkEmailField() {
        EditText emailText = findViewById(R.id.name);
        if (emailText.getText().toString().isEmpty()) {
            emailText.setError("Name is empty");
            fieldError = true;
        }
        /*if (!Pattern.matches("(\\w|-)+@\\w+.(com|es)", email)) {
            emailText.setError("Email doesn't exists");
            fieldError = true;
        } else if (email.length() <= 3) {
            emailText.setError("Email too short");
            fieldError = true;
        } else if (email.length() > 50) {
            emailText.setError("Email too long");
            fieldError = true;
        } else if (emailExists(email)) {
            emailText.setError("Email already exists");
            fieldError = true;
        }*/

    }

    private boolean emailExists(String email) {
        if (email.equals("Existe")) {
            return true;
        }
        return false;
    }

    private void createEnterprise() {
        EditText text = findViewById(R.id.name);
        String name = text.getText().toString();

        text = findViewById(R.id.password);
        String pass = text.getText().toString();

        text = findViewById(R.id.nif);
        String nif = text.getText().toString();

        text = findViewById(R.id.description);
        String description = text.getText().toString();

        text = findViewById(R.id.phone);
        String phone = text.getText().toString();

        text = findViewById(R.id.address);
        String address = text.getText().toString();

        if (!dbHelper.checkIfNifExist(nif)) {
            if (isSwitchChecked) {
                dbHelper.addData(name,nif,pass,description,phone,address,"Restaurant");
            } else {
                dbHelper.addData(name,nif,pass,description,phone,address,"Enterprise");
            }
        } else {
            text = findViewById(R.id.nif);
            text.setError("Nif already registered in database");
        }


    }


    public void goBack(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

}
