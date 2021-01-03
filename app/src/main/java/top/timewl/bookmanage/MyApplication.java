package top.timewl.bookmanage;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static MyApplication myApplication;
    private final String localHost = "http://192.168.1.238:8000/";

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    public static MyApplication getInstance(){
        return myApplication;
    }

    public String getLocalHost(){
        return localHost;
    }
}
