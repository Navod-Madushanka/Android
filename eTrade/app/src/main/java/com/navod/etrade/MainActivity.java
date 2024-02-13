package com.navod.etrade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.navod.etrade.adapter.HomePageAdapter;
import com.navod.etrade.util.ImageColorUtil;
import com.navod.etrade.util.SecureApp;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private ViewPager viewPager;
    public ImageView home, search, user, notification;
    private ImageColorUtil imageColorUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!SecureApp.validUser(this)){
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        }

        initView();

        imageColorUtil = new ImageColorUtil(this);

        viewPager = findViewById(R.id.viewPagerUpdateUser);
        HomePageAdapter homePageAdapter = new HomePageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(homePageAdapter);

        setListeners();

    }
    private void setListeners(){
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateImageViews(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void updateImageViews(int position){
        resetImageViews();
        switch (position){
            case 0:
                imageColorUtil.changeColor(home);
                break;
            case 1:
                imageColorUtil.changeColor(search);
                break;
            case 2:
                imageColorUtil.changeColor(user);
                break;
            case 3:
                imageColorUtil.changeColor(notification);
                break;
        }
    }

    private void resetImageViews(){
        imageColorUtil.resetImages(home, search, user, notification);
    }
    private void initView(){
        home = findViewById(R.id.imageView3);
        search = findViewById(R.id.imageView4);
        user = findViewById(R.id.imageView5);
        notification = findViewById(R.id.imageView6);
    }
}