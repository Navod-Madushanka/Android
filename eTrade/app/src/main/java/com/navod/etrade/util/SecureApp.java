package com.navod.etrade.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecureApp {
    public static String encrypt(String source){
        String md5 = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(source.getBytes());
            byte[] hashBytes = digest.digest();

            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                hexStringBuilder.append(String.format("%02x", b));
            }
            md5 = hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return md5;
    }
    public static boolean isValidEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean validUser(Context context){
        String userId = SharedPreferencesManager.getUserId(context);
        if(userId != null){
            return true;
        }
        return false;
    }
}
