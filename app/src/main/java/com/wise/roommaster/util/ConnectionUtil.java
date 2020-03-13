package com.wise.roommaster.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

public final class ConnectionUtil {
    /**
     *
     */
    public static boolean hasConnection(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        if (cm != null) {
            networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }
    //public static boolean hasConnection(Context context, int type){
   //    return false;
    //}

}
