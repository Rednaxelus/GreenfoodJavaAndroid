package com.example.greenfoodjava.ui.login;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.example.greenfoodjava.R;
import com.example.greenfoodjava.database.UserTable;

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
        if (allFieldsHaveCorrectFormat() && !userExist()){
            registerUserInUserTable();
        }
    }

    private void registerUserInUserTable() {
        String email = getField(R.id.email);
        String password = getField(R.id.password);
        String name = getField(R.id.name);
        String surname = getField(R.id.surname);
        UserTable userTable = new UserTable(null);
        userTable.addData(email, password, name, surname);
    }

    private boolean userExist() {
        String email = getField(R.id.email);
        UserTable userTable = new UserTable(null);
        return userTable.checkIfUserExist(email);
    }

    private boolean allFieldsHaveCorrectFormat() {
        return emailHasCorrectFormat() & nameIsComplete() & surnameIsComplete()
                & passwordHasCorrectFormat();
    }

    private String getField(int id){
        TextView field = findViewById(id);
        return field.getText().toString();
    }

    private void showErrorFor(int id, String message){
        TextView field = findViewById(id);
        field.setError(message);
    }

    private boolean emailHasCorrectFormat() {
        String email = getField(R.id.email);
        if(!emailIsValid(email)){
            showErrorFor(R.id.email, "Invalid email");
            return false;
        }
        return true;
    }

    private boolean emailIsValid(String email) {
        return email.matches("(\\w|-)+@\\w+\\.(com|es)");
    }

    private boolean nameIsComplete() {
        String name = getField(R.id.name);
        if (name.isEmpty()){
            showErrorFor(R.id.name, "Name is empty");
            return false;
        }
        return true;
    }

    private boolean surnameIsComplete() {
        String surname = getField(R.id.surname);
        if (surname.isEmpty()){
            showErrorFor(R.id.surname, "Surname is empty");
            return false;
        }
        return true;
    }

    private boolean passwordHasCorrectFormat() {
        String password = getField(R.id.password);
        if (!passwordIsValid(password)){
            showErrorFor(R.id.password, "Invalid password");
            return false;
        }
        return false;
    }

    private boolean passwordIsValid(String password) {
        return password.matches("([A-Za-z]|\\d|.){8,}");
    }
}
