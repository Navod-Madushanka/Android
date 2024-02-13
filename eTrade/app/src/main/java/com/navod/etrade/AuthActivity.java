package com.navod.etrade;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.navod.etrade.util.SecureApp;

public class AuthActivity extends AppCompatActivity {
    private static final String TAG = AuthActivity.class.getName();
    private FirebaseAuth firebaseAuth;
    private SignInClient signInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if(SecureApp.validUser(this)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        Button btnSignIn = findViewById(R.id.btnSignIn);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView textViewGoogle = findViewById(R.id.textViewGoogle);

//        LogInBtn
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<LogIn> logInClass = LogIn.class;
                changeFragment(logInClass);
                textViewGoogle.setText(getResources().getString(R.string.log_in_with_google_account));
                changeActiveFragmentBtnColor(btnSignIn, btnLogin);
            }
        });

//        SignInBtn
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<SignInFragment> signInFragmentClass = SignInFragment.class;
                changeFragment(signInFragmentClass);
                textViewGoogle.setText(getResources().getString(R.string.sign_in_with_google_account));
                changeActiveFragmentBtnColor(btnSignIn, btnLogin);
            }
        });

        findViewById(R.id.layoutConstrainGoogle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                signInClient = Identity.getSignInClient(getApplicationContext());

                GetSignInIntentRequest signInIntentRequest = GetSignInIntentRequest.builder()
                        .setServerClientId(getString(R.string.web_client_id)).build();
                Task<PendingIntent> signInIntent = signInClient.getSignInIntent(signInIntentRequest);
                signInIntent.addOnSuccessListener(new OnSuccessListener<PendingIntent>() {
                    @Override
                    public void onSuccess(PendingIntent pendingIntent) {
                        IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(pendingIntent).build();
                        signInLauncher.launch(intentSenderRequest);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Firebase Authentication Failed: " + e.getMessage());
                        Toast.makeText(AuthActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    private final ActivityResultLauncher<IntentSenderRequest> signInLauncher =
            registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult o) {
                            handleSignInResult(o.getData());
                        }
                    });
    private void handleSignInResult(Intent intent) {
        try {
            SignInCredential signInCredential = signInClient.getSignInCredentialFromIntent(intent);
            if (signInCredential != null) {
                String idToken = signInCredential.getGoogleIdToken();
                firebaseAuthWithGoogle(idToken);
            } else {
                Log.e(TAG, "SignInCredential is null");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error handling SignInResult: " + e.getMessage());
        }
    }
    private void firebaseAuthWithGoogle(String idToken){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken, null);
        Task<AuthResult> authResultTask = firebaseAuth.signInWithCredential(authCredential);
        authResultTask.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void updateUI(FirebaseUser user){
        if(user!=null){
            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
    private void changeActiveFragmentBtnColor(Button activeBtn, Button inActiveBtn){
        activeBtn.setBackgroundColor(getResources().getColor(R.color.subBtnActive));
        inActiveBtn.setBackgroundColor(getResources().getColor(R.color.subBtnInActive));
    }

    private void changeFragment(Class fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView4, fragment, null, "tag")
                .setReorderingAllowed(true)
                .addToBackStack("replacement")
                .commit();
    }
}