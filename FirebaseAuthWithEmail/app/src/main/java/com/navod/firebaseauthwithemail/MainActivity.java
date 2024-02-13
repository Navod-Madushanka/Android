package com.navod.firebaseauthwithemail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        getDataFormUI();
    }
    private void getDataFormUI(){
        EditText emailEditText = findViewById(R.id.editTextEmail);
        EditText passwordEditText = findViewById(R.id.editTextPassword);

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authWithEmailAndPassword(emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAction(emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }
    private void loginAction(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            updateUI(firebaseAuth.getCurrentUser());
                        }
                    }
                });
    }
    private void authWithEmailAndPassword(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i(TAG, "createUserWithEMailAndPassword:Success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            user.sendEmailVerification();
//                            updateUI(user);
                            Toast.makeText(MainActivity.this, "Please verify your email", Toast.LENGTH_LONG).show();
                            findViewById(R.id.register).setVisibility(View.GONE);
                        }else{
                            Log.w(TAG, "createUserWithEmailANdPassword:Failed");
                            Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user){
        if(user!=null){
            if(!user.isEmailVerified()){
                Toast.makeText(MainActivity.this, "Please verify your email", Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}