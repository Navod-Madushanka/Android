package com.navod.etrade.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.navod.etrade.entity.User;

public class SharedUserViewModel extends AndroidViewModel {
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public SharedUserViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void setUser(User user) {
        userLiveData.setValue(user);
    }
}

