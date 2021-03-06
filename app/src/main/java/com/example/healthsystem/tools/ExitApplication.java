package com.example.healthsystem.tools;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 * 退出应用
 */

public class ExitApplication extends Application {
    private static ExitApplication instance;
    private List<Activity> activitiesList = new LinkedList<Activity>();

    private ExitApplication() {

    }

    public static ExitApplication getInstance() {
        if (null == instance) {
            instance = new ExitApplication();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        activitiesList.add(activity);
    }

    public void exit() {
        for (Activity activity : activitiesList) {
            activity.finish();
        }
        System.exit(0);
    }
}
