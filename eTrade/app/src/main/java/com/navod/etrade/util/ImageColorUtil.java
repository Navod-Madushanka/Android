package com.navod.etrade.util;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.navod.etrade.R;

public class ImageColorUtil {
    private Context context;
    public ImageColorUtil(Context context){
        this.context = context;
    }
    public void changeColor(ImageView image){
        Drawable drawable = image.getDrawable();
        int color = ContextCompat.getColor(context, R.color.colorPrimary);
        applyColorFilter(drawable, color);
    }
    public void resetImages(ImageView... images){
        int color = ContextCompat.getColor(context, R.color.buttonText);
        for(ImageView image : images){
            Drawable drawable = image.getDrawable();
            applyColorFilter(drawable, color);
            image.invalidate();
        }
    }
    private void applyColorFilter(Drawable drawable, int color){
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
        drawable.setColorFilter(porterDuffColorFilter);
    }
}
