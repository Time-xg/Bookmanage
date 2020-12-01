package top.timewl.bookmanage;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static MyApplication myApplication;

    public static Context getInstance(){
        if (myApplication == null){
            myApplication = new MyApplication();

        }
        return myApplication;
    }
}
