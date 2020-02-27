package com.wise.roommaster.util;

import android.content.SharedPreferences;

public class Globals {

    public static int userId;
    public static String userName;
    public static String userEmail;
    public static boolean remindEmail = false;
    public static boolean autoLogin = false;
    public static boolean logged = false;

    public static int companyId;
    public static String companyName;


    public static void update(SharedPreferences preferences){
        userId = preferences.getInt("userId", -1);
        userName = preferences.getString("userName", null);
        userEmail = preferences.getString("userEmail", null);
        autoLogin = preferences.getBoolean("autoLogin", false);
        remindEmail = preferences.getBoolean("remindEmail", false);

        companyId = preferences.getInt("companyId", -1);
        companyName = preferences.getString("companyName", null);
        printAll();
    }
    public static void reset(){
        userId = -1;
        userName = null;
        userEmail = null;
        autoLogin = false;
        remindEmail = false;
        companyId = -1;
        logged = false;
        companyName = null;
        printAll();
    }
    public static void printAll(){
        System.out.println("----------------------GLOBALS----------------------");
        System.out.println("userId: " + userId);
        System.out.println("userName: " + userName);
        System.out.println("userEmail: " + userEmail);
        System.out.println("remindEmail: " + remindEmail);
        System.out.println("autoLogin: " + autoLogin);
        System.out.println("logged: " + logged);
        System.out.println("companyId: " + companyId);
        System.out.println("companyName: " + companyName);
        System.out.println("---------------------------------------------------");
    }

    public static void setUserId(int userId) {
        Globals.userId = userId;
    }

    public static void setUserName(String userName) {
        Globals.userName = userName;
    }

    public static void setUserEmail(String userEmail) {
        Globals.userEmail = userEmail;
    }
    public static void setRemindEmail(boolean remindEmail){
        Globals.remindEmail = remindEmail;
    }
    public static Boolean isEmailReminded(){
        return remindEmail;
    }

    public static void setAutoLogin(boolean autoLogin) {
        Globals.autoLogin = autoLogin;
    }
    public static Boolean isAutoLoginEnabled(){
        return autoLogin;
    }
    public static Boolean isLogged(){
        return logged;
    }

    public static int getCompanyId() {
        return companyId;
    }

    public static void setCompanyId(int companyId) {
        Globals.companyId = companyId;
    }

    public static void setCompanyName(String companyName) {
        Globals.companyName = companyName;
    }
}
