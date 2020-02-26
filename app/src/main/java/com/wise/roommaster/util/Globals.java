package com.wise.roommaster.util;

import android.content.SharedPreferences;

public class Globals {

    public static int userId;
    public static String userName;
    public static String userEmail;
    public static boolean autoLogin = false;


    public static int companyId;
    public static String companyName;

    public static void update(SharedPreferences preferences){
        userId = preferences.getInt("userId", -1);
        userName = preferences.getString("userName", null);
        userEmail = preferences.getString("userEmail", null);
        autoLogin = preferences.getBoolean("autoLogin", false);
        companyId = preferences.getInt("companyId", -1);
        companyName = preferences.getString("companyName", null);
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        Globals.userId = userId;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Globals.userName = userName;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String userEmail) {
        Globals.userEmail = userEmail;
    }

    public static boolean isAutoLogin() {
        return autoLogin;
    }

    public static void setAutoLogin(boolean autoLogin) {
        Globals.autoLogin = autoLogin;
    }

    public static int getCompanyId() {
        return companyId;
    }

    public static void setCompanyId(int companyId) {
        Globals.companyId = companyId;
    }

    public static String getCompanyName() {
        return companyName;
    }

    public static void setCompanyName(String companyName) {
        Globals.companyName = companyName;
    }
}
