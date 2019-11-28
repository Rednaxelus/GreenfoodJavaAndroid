package ui.loginAndRegistration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.greenfoodjava.R;

import database.DatabaseManager;
import database.EnterpriseTable;
import database.UserTable;
import ui.enterprise.EnterpriseHomeActivity;
import ui.user.UserHomeActivity;

public class LoginActivity extends Activity {
    private UserTable userTable;
    private EnterpriseTable enterpriseTable;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    private int userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        userTable = DatabaseManager.getUserTable();
        enterpriseTable = new EnterpriseTable(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        this.userId = -1;
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Registration");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_menu_back_button);
        toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserRegistrationActivity.class)));
    }

    public void goBack(View view) {
        startActivity(new Intent(this, UserRegistrationActivity.class));
    }

    public void loginProcess(View v){
        if (allFieldsHaveCorrectFormat()){
            if (userExist()){
                setCredentials();
                startActivity(new Intent(this, UserHomeActivity.class));
            }else if (enterpriseExist()){
                setCredentials();
                startActivity(new Intent(this, EnterpriseHomeActivity.class));
            }else{
                showErrorFor(R.id.email, "User doesn't exist");
                showErrorFor(R.id.password, "User doesn't exist");
            }
        }
    }

    private void setCredentials() {
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("email", getField(R.id.email));
        editor.putInt("id", this.userId);
        editor.commit();
    }

    private boolean enterpriseExist() {
        String email = getField(R.id.email);
        String password = getField(R.id.password);
        this.userId = enterpriseTable.checkIfUserExist(email, password);
        return this.userId != -1;
    }

    private boolean userExist() {
        String email = getField(R.id.email);
        String password = getField(R.id.password);
        this.userId = userTable.checkIfUserExist(email, password);
        return this.userId != -1;

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
