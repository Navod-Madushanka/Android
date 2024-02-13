package com.navod.etradeadmin.CallBack;

import java.util.List;

public interface ImageListCallback {
    void onSuccess(List<String> downloadUrlList);
    void onFailure(String error);
}
