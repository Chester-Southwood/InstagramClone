package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    private static final String   TAG = "loginActivity";

    private              EditText etUserName;
    private              EditText etPassword;
    private              Button   btnLogin;
    private              Button   btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin   = findViewById((R.id.btnLogin));
        btnSignup  = findViewById(R.id.btnSignup);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            //Toast.makeText(this, currentUser.getUsername(), Toast.LENGTH_LONG).show();
            goMainActivity();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                login(userName, password);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName = etUserName.getText().toString();
                final String password = etPassword.getText().toString();

                // Create the ParseUser
                ParseUser user = new ParseUser();
                // Set core properties
                user.setUsername(userName);
                user.setPassword(password);

                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            login(userName, password);
                        } else {
                            Toast.makeText(LoginActivity.this, "Error:Please Contact Your Administrator!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "ISSUE WITH Signup!!!");
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void login(String userName, String password) {
        Log.e("PASSWORD", password);
        ParseUser.logInInBackground(userName, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null) {
                    if(e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                        printLoginErrorToast();
                    } else {
                        printUnknownErrorToast(TAG, e);
                    }
                    return;
                } else {
                    goMainActivity();
                }
            }
        });
    }

    private void printLoginErrorToast() {
        Toast.makeText(this, "Error:Incorrect Credentials!", Toast.LENGTH_LONG).show();
    }

    private void printUnknownErrorToast(String TAG, ParseException e) {
        Toast.makeText(this, "Error:Please Contact Your Administrator!", Toast.LENGTH_LONG).show();
        Log.e(TAG, "ISSUE WITH LOGIN!!!");
        e.printStackTrace();
    }
    private void goMainActivity() {
        Toast.makeText(this, "Successfully Signed In!", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
