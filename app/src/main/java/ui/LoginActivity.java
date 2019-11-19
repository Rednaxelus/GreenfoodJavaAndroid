package ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.greenfoodjava.R;

import database.EnterpriseTable;
import database.UserTable;

public class LoginActivity extends Activity {
    private UserTable userTable;
    private EnterpriseTable enterpriseTable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        userTable = new UserTable(this);
        enterpriseTable = new EnterpriseTable(this);
    }

    public void goBack(View view) {
        startActivity(new Intent(this, UserRegistrationActivity.class));
    }

    public void loginProcess(View v){
        if (allFieldsHaveCorrectFormat()){
            if (userExist()) ;
            if (enterpriseExist());
        }
    }

    private boolean enterpriseExist() {
        return false;
    }

    private boolean userExist() {
        String email = getField(R.id.email);
        if (userTable.checkIfUserExist(email)){
            return false;
        }
        return true;
    }

    private boolean allFieldsHaveCorrectFormat() {
        return emailHasCorrectFormat() & !passwordIsEmpty();
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

    private boolean passwordIsEmpty() {
        String password = getField(R.id.password);
        if (passwordIsEmpty(password)){
            showErrorFor(R.id.password, "Empty password");
            return false;
        }
        return true;
    }

    private boolean passwordIsEmpty(String password) {
        return password.isEmpty();
    }
}
