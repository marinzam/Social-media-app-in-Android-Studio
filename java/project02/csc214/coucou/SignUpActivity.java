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

public class SignUpActivity extends AppCompatActivity {

    public static DataAccessObject sDataAccessObject;

    EditText editEmail;
    EditText editUserName;
    EditText editPassword;

    Button signUpButton;
    Button cancelButton;

    String mEmail;
    String mUserName;
    String mPassword;

    public final static String EDIT_EMAIL = "EDIT_EMAIL";
    public final static String EDIT_USER_NAME = "EDIT_USER_NAME";
    public final static String EDIT_PASSWORD = "EDIT_PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sDataAccessObject = new DataAccessObject(getApplicationContext());

        editEmail = (EditText) findViewById(R.id.edit_new_email);
        editUserName = (EditText) findViewById(R.id.edit_new_user_name);
        editPassword = (EditText) findViewById(R.id.edit_new_password);

        signUpButton = (Button) findViewById(R.id.button_sign_up_new);
        cancelButton = (Button) findViewById(R.id.button_cancel_new);

        if(savedInstanceState != null) {
            editEmail.setText(savedInstanceState.getString(EDIT_EMAIL));
            editUserName.setText(savedInstanceState.getString(EDIT_USER_NAME));
            editPassword.setText(savedInstanceState.getString(EDIT_PASSWORD));
        }

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmail = editEmail.getText().toString();
                mUserName = editUserName.getText().toString();
                mPassword = editPassword.getText().toString();

                if(mEmail.isEmpty() || mUserName.isEmpty() || mPassword.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Email, Username, and Password are required.",
                            Toast.LENGTH_SHORT).show();
                }

                //check if valid email
                if(!mEmail.contains("@")) {
                    Toast.makeText(SignUpActivity.this, "Invalid email.",
                            Toast.LENGTH_SHORT).show();
                }

                //check if password is of length 5 or more
                if(mPassword.length() < 5) {
                    Toast.makeText(SignUpActivity.this, "Password length too small.",
                            Toast.LENGTH_SHORT).show();
                }

                User user = sDataAccessObject.getUserFromUserName(mUserName);
                if(user != null) {
                    Toast.makeText(SignUpActivity.this, "Username is taken.", Toast.LENGTH_SHORT).show();
                }

                user = sDataAccessObject.getUserFromEmail(mEmail);
                if(user != null) {
                    Toast.makeText(SignUpActivity.this, "Email is taken.", Toast.LENGTH_SHORT).show();
                }

                User newUser = new User();
                newUser.setUserName(mUserName);
                newUser.setEmail(mEmail);
                newUser.setPassword(mPassword);
                sDataAccessObject.insertUser(newUser);

                //go to editInfo activity
                Intent intent = new Intent(SignUpActivity.this, EditInfoActivity.class);
                Login login = new Login(mUserName, true);
                sDataAccessObject.insertLoggedIn(login);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);

        savedState.putString(EDIT_EMAIL, mEmail);
        savedState.putString(EDIT_USER_NAME, mUserName);
        savedState.putString(EDIT_PASSWORD, mPassword);
    }
}
