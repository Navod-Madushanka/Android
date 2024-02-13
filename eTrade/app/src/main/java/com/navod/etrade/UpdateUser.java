package com.navod.etrade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.navod.etrade.adapter.UpdateUserAdapter;
import com.navod.etrade.callback.UserCallback;
import com.navod.etrade.callback.UserUpdateCallback;
import com.navod.etrade.entity.User;
import com.navod.etrade.listener.OnUserUpdateListener;
import com.navod.etrade.model.SharedUserViewModel;
import com.navod.etrade.service.UserService;
import com.navod.etrade.util.SharedPreferencesManager;

public class UpdateUser extends AppCompatActivity implements OnUserUpdateListener {
    private static final String TAG = UpdateUser.class.getName();
    public ViewPager viewPager;
    private UserService userService;
    private SharedUserViewModel sharedUserViewModel;
    private String userId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        sharedUserViewModel = new ViewModelProvider(this).get(SharedUserViewModel.class);

        userService = new UserService(UpdateUser.this);

        getUser();

        viewPager = findViewById(R.id.viewPagerUpdateUser);
        UpdateUserAdapter updateUserAdapter = new UpdateUserAdapter(getSupportFragmentManager());
        viewPager.setAdapter(updateUserAdapter);

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateUser.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getUser(){
        userId = SharedPreferencesManager.getUserId(UpdateUser.this);
        userService.getUserById(userId, new UserCallback() {
            @Override
            public void onUserResult(User existUser) {
                sharedUserViewModel.setUser(existUser);
                Log.i(TAG, existUser.toString());
            }
        });
    }

    @Override
    public void onUserUpdated(User updatedUser) {
        if(updatedUser != null){
            Log.i(TAG, " Updated user: "+updatedUser.toString());
        }
    }

    @Override
    public void handleUpdatedUser(User user) {
        if(user != null){
            Log.i(TAG, "Handel Updated user: "+user.toString());
            if(user.getProfilePictureUrl() != null){
                userService.updateUser(userId, user, new UserUpdateCallback() {
                    @Override
                    public void onSuccess(String successMessage) {
                        userService.updateImageTemplate(userId, user.getProfilePictureUrl());
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.i(TAG, "Failed to update user");
                    }
                });
            }
        }
    }
}