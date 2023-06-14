package com.app.myapplication.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {
    private static SharedPreferences mySharedPreferences;
    private static String PREF = "pref";
    private static String IDUSER= "idUser";

    public static void setUserId(Context context, String id){
        mySharedPreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = mySharedPreferences.edit();
        myEditor.putString(IDUSER, id);
        myEditor.commit();
    }

    public static String getUserId(Context context){
        mySharedPreferences = context.getSharedPreferences(PREF, 0);
        return mySharedPreferences.getString(IDUSER, null);
        // return "1";
    }
 

}
