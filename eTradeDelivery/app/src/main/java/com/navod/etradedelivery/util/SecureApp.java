package com.navod.etradedelivery.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecureApp {
    public static boolean access = false;
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

    public static void setAccess(boolean action){
        access = action;
    }
    public static String generateRandomPassword(int length){
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomPassword = new StringBuilder();
        for(int i = 0; i < length; i++){
            int index = (int) (Math.random()*allowedCharacters.length());
            randomPassword.append(allowedCharacters.charAt(index));
        }
        return randomPassword.toString();
    }

}
