package com.navod.etradeadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.navod.etradeadmin.service.AdminService;
import com.navod.etradeadmin.util.ReLoadLogin;
import com.navod.etradeadmin.util.SecureApp;

public class MainActivity extends AppCompatActivity {
    private int maxAttempt = 3;
    private int attempt = 0;
    private static final String TAG = MainActivity.class.getName();
    TextView username, password;
    private int time = 5*60*1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SecureApp.access){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        findViewById(R.id.btnLogIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAction();
            }
        });
    }
    private void loginAction(){
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);

        AdminService adminService = new AdminService();
        boolean logIn = adminService.logIn(SecureApp.encrypt(username.getText().toString()),
                SecureApp.encrypt(password.getText().toString()));
        Log.i(TAG, String.valueOf(logIn));
        if(logIn){
            goToHome();
        }else{
            LogInError();
        }
    }

    private void LogInError(){
        attempt++;
        TextView errorMessage = findViewById(R.id.txtError);

        if(attempt < maxAttempt){
            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText("Incorrect credentials. Attempts left:"+ (maxAttempt - attempt));
        }else{
            errorMessage.setText("Incorrect credentials. Log In Aborted for 5 Minutes");
            username.setEnabled(false);
            password.setEnabled(false);

            new Handler().postDelayed(new ReLoadLogin(MainActivity.this), time);
        }
    }

    private void goToHome(){
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}