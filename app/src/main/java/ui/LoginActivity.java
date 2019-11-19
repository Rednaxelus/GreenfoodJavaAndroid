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
            if (userExist()){
                startActivity(new Intent(this, UserHomeActivity.class));
            }else if (enterpriseExist()){
                startActivity(new Intent(this, EnterpriseHomeActivity.class));
            }else{
                showErrorFor(R.id.email, "User doesn't exist");
                showErrorFor(R.id.password, "User doesn't exist");
            }
        }
    }

    private boolean enterpriseExist() {
        String email = getField(R.id.email);
        String password = getField(R.id.password);
        if (!enterpriseTable.checkIfUserExist(email, password)){
            return false;
        }
        return true;
    }

    private boolean userExist() {
        String email = getField(R.id.email);
        String password = getField(R.id.password);
        if (!userTable.checkIfUserExist(email, password)){
            return false;
        }
        return true;
    }

    private boolean allFieldsHaveCorrectFormat() {
        return emailHasCorrectFormat() & passwordIsNotEmpty();
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

    private boolean passwordIsNotEmpty() {
        String password = getField(R.id.password);
        if (passwordIsNotEmpty(password)){
            showErrorFor(R.id.password, "Empty password");
            return false;
        }
        return true;
    }

    private boolean passwordIsNotEmpty(String password) {
        return password.isEmpty();
    }
}
