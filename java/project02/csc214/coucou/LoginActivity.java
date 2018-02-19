package project02.csc214.coucou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import project02.csc214.coucou.Model.Login;
import project02.csc214.coucou.Model.User;

public class LoginActivity extends AppCompatActivity {
    public static DataAccessObject sDataAccessObject;

    EditText editUserName;
    EditText editPassword;
    Button loginButton;
    Button signUpButton;

    String mUserName;
    String mPassword;

    static String EDIT_USER_NAME = "EDIT_USER_NAME";
    static String EDIT_PASSWORD = "EDIT_PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUserName = (EditText) findViewById(R.id.edit_user_name);
        editPassword = (EditText) findViewById(R.id.edit_password);

        loginButton = (Button) findViewById(R.id.button_login);
        signUpButton = (Button) findViewById(R.id.button_sign_up);

        sDataAccessObject = new DataAccessObject(getApplicationContext());

        if(savedInstanceState != null) {
            editUserName.setText(savedInstanceState.getString(EDIT_USER_NAME));
            editPassword.setText(savedInstanceState.getString(EDIT_PASSWORD));
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUserName = editUserName.getText().toString();
                mPassword = editPassword.getText().toString();

                if(mUserName.isEmpty() || mPassword.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Username and password required.", Toast.LENGTH_SHORT).show();
                }

                //if user exists and password matches, start news feed activity
                if(sDataAccessObject.getUser(mUserName, mPassword) != null) {
                    Toast.makeText(LoginActivity.this, "Logging in...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, NewsFeedActivity.class);
                    Login login = new Login(mUserName, true);
                    sDataAccessObject.insertLoggedIn(login);
                    startActivity(intent);
                }

                //OTHERWISE...

                //check only username in database
                User user = sDataAccessObject.getUserFromUserName(mUserName);
                if(user == null) {
                    Toast.makeText(LoginActivity.this, "Username does not exist.", Toast.LENGTH_SHORT).show();
                } else
                    // check corresponding password
                    if(!user.getPassword().equals(mPassword)) {
                        Toast.makeText(LoginActivity.this, "Password incorrect.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "DEBUG: Last else statement.", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);

        savedState.putString(EDIT_USER_NAME, mUserName);
        savedState.putString(EDIT_PASSWORD, mPassword);
    }
}
