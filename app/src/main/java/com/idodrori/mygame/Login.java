package com.idodrori.mygame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.idodrori.mygame.services.DatabaseService;

public class Login extends AppCompatActivity implements View.OnClickListener {



    private static final String TAG = "LoginActivity";

    private DatabaseService databaseService;
    private EditText etEmail, etPassword;
    private Button btnLogin;

    public static final String MyPREFERENCES = "MyPrefs" ;

    static final String ADMINEMAIL = "idodrori29@gmail.com";
    static final String ADMINPASS = "adminido29";
    SharedPreferences sharedpreferences;
    Button  btnRegister;
    private String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        /// get the views
        databaseService = DatabaseService.getInstance();

        etEmail = findViewById(R.id.etFname);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.loginBtn);
        btnRegister = findViewById(R.id.btnRegister);



         email = sharedpreferences.getString("email", "");
        password = sharedpreferences.getString("password", "");
        etEmail.setText(email);
        etPassword.setText(password);

        /// set the click listener
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnLogin.getId()) {
            Log.d(TAG, "onClick: Login button clicked");

            /// get the email and password entered by the user
             email = etEmail.getText().toString();
             password = etPassword.getText().toString();

            if (email.equals(ADMINEMAIL) && password.equals(ADMINPASS)) {
                Log.d(TAG, "user is admin");
           //     Intent registerIntent = new Intent(Login.this, AdminMainActivity.class);
          //      startActivity(registerIntent);
            }
            else {
                /// log the email and password
                Log.d(TAG, "onClick: Email: " + email);
                Log.d(TAG, "onClick: Password: " + password);

                Log.d(TAG, "onClick: Validating input...");
                /// Validate input
                Log.d(TAG, "onClick: Logging in user...");

                /// Login user
                loginUser(email, password);
            }
        } else if (v.getId() == btnRegister.getId()) {
            /// Navigate to Register Activity
            Intent registerIntent = new Intent(Login.this, RegisterActivity.class);
            startActivity(registerIntent);
        }
    }
    private void loginUser(String email, String password) {
        databaseService.LoginUser(email, password, new DatabaseService.DatabaseCallback<String>() {
            /// Callback method called when the operation is completed
            @Override
            public void onCompleted(String  uid) {
                Log.d(TAG, "onCompleted: User logged in: " + uid.toString());


                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString("email", email);
                editor.putString("password", password);

                editor.commit();
                /// save the user data to shared preferences
                // SharedPreferencesUtil.saveUser(LoginActivity.this, user);
                /// Redirect to main activity and clear back stack to prevent user from going back to login screen
                Intent mainIntent = new Intent(Login.this, MainActivity.class);
                /// Clear the back stack (clear history) and start the MainActivity
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "onFailed: Failed to retrieve user data", e);
                /// Show error message to user
                etPassword.setError("אימייל או סיסמה לא נכונים  " +
                        "" +
                        "");
                etPassword.requestFocus();
                /// Sign out the user if failed to retrieve user data
                /// This is to prevent the user from being logged in again
                //SharedPreferencesUtil.signOutUser(LoginActivity.this);
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}



