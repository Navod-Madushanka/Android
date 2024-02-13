package com.navod.etradeadmin.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class CurrentDate {
    public static Date getCurrentDate(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime now = LocalDateTime.now();
            return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }
}
