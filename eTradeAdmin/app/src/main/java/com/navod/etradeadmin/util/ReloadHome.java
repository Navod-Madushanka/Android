package com.navod.etradeadmin.util;

import android.content.Intent;
import android.os.Handler;

import com.navod.etradeadmin.HomeActivity;
import com.navod.etradeadmin.MainActivity;

public class ReloadHome implements Runnable{
    private HomeActivity homeActivity;
    public ReloadHome(HomeActivity homeActivity){
        this.homeActivity = homeActivity;
    }
    @Override
    public void run() {
        homeActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                homeActivity.finish();
                Intent intent = new Intent(homeActivity, HomeActivity.class);
                homeActivity.startActivity(intent);
            }
        });
    }
}
