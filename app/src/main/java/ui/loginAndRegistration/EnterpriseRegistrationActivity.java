package ui.loginAndRegistration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.greenfoodjava.R;

import java.util.regex.Pattern;

import database.EnterpriseTable;
import ui.enterprise.EnterpriseHomeActivity;

import static ui.loginAndRegistration.LoginActivity.MyPREFERENCES;

public class EnterpriseRegistrationActivity extends Activity {
    private boolean isSwitchChecked;
    private boolean fieldError = false;
    private EnterpriseTable dbHelper;
    private int ID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterprise_registration);
        initSwitchListener();
        dbHelper = new EnterpriseTable(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("GreenFood");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_menu_back_button);
        toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(getApplicationContext(),UserRegistrationActivity.class)));
    }

    public void goBack(View view) {
        startActivity(new Intent(this, UserRegistrationActivity.class));
    }

    private void initSwitchListener() {
        Switch s = findViewById(R.id.switch1);
        s.setOnCheckedChangeListener((buttonView, isChecked) -> isSwitchChecked = isChecked);
        isSwitchChecked = false;
    }

    public void register(View view) {
        fieldError = false;
        checkEmailField();

        checkNameField();
        checkPassField();
        checkCifField();
        checkDescriptionField();
        checkPhoneField();
        checkAddressField();
        if (!fieldError) {
            createEnterprise();
            setCredentials();
            if(ID != -1)
                startActivity(new Intent(this, EnterpriseHomeActivity.class));
        }
    }

    private void setCredentials() {
        SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();

        editor.putString("email", getField(R.id.email));
        editor.putInt("id", this.ID);
        editor.commit();
    }

    private String getField(int id){
        TextView field = findViewById(id);
        return field.getText().toString();
    }


    private void checkNameField() {
        EditText nameText = findViewById(R.id.name);
        String name = nameText.getText().toString();
        if (name.isEmpty()) {
            nameText.setError("Name empty");
            fieldError = true;
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

    private void checkCifField() {
        EditText cifText = findViewById(R.id.cif);
        String cif = cifText.getText().toString();
        if (cif.length() != 9) {
            cifText.setError("Wrong cif");
            fieldError = true;
        } else if (!Pattern.matches("([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke]\\d{8})", cif)) {
            cifText.setError("Wrong cif");
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

    private boolean emailExists() {
        EditText emailText = findViewById(R.id.email);
        return dbHelper.checkIfEnterpriseExist(emailText.getText().toString());

    }

    private void checkEmailField() {
        EditText emailText = findViewById(R.id.email);
        String email = emailText.getText().toString();
        if (!Pattern.matches("(\\w|-)+@\\w+.(com|es)", email)) {
            emailText.setError("Email doesn't exists");
            fieldError = true;
        } else if (email.length() <= 3) {
            emailText.setError("Email too short");
            fieldError = true;
        } else if (email.length() > 50) {
            emailText.setError("Email too long");
            fieldError = true;
        } else if (emailExists()) {
            emailText.setError("Email already registered");
            fieldError = true;
        }
    }

    private void createEnterprise() {

        EditText text = findViewById(R.id.email);
        String email = text.getText().toString();

        text = findViewById(R.id.name);
        String name = text.getText().toString();

        text = findViewById(R.id.password);
        String pass = text.getText().toString();

        text = findViewById(R.id.cif);
        String cif = text.getText().toString();

        text = findViewById(R.id.description);
        String description = text.getText().toString();

        text = findViewById(R.id.phone);
        String phone = text.getText().toString();

        text = findViewById(R.id.address);
        String address = text.getText().toString();

        if (isSwitchChecked) {
            this.ID = dbHelper.addData(email, name, cif, pass, description, phone, address, "Restaurant");
        } else {
            this.ID = dbHelper.addData(email, name, cif, pass, description, phone, address, "Enterprise");
        }
    }
}
