package com.navod.etradeadmin.service;

import com.navod.etradeadmin.util.SecureApp;

public class AdminService {
    private final String username = SecureApp.encrypt("eTrade");
    private final String password = SecureApp.encrypt("eTrade1234");

    public boolean logIn(String username, String password){
        if(username.equals(this.username) && password.equals(this.password)){
            SecureApp.setAccess(true);
            return true;
        }else{
            return false;
        }
    }
}
