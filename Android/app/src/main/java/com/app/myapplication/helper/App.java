package com.app.myapplication.helper;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    public static Application mInstance = new Application();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static Context getContext(){
        return mInstance.getApplicationContext();
    }
}
