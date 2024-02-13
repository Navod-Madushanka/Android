package com.navod.etradeadmin.util;

import android.content.Intent;

import com.navod.etradeadmin.MainActivity;

public class ReLoadLogin implements Runnable{
    private MainActivity mainActivity;
    public ReLoadLogin(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    @Override
    public void run() {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.finish();
                Intent intent = new Intent(mainActivity, MainActivity.class);
                mainActivity.startActivity(intent);
            }
        });
    }
}
