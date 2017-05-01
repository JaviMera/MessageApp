package com.teamtreehouse.ribbit;

import android.app.Application;

/**
 * Created by benjakuben on 10/12/16.
 */

public class RibbitApplication extends Application {

    public static String PACKAGE_NAME;

    @Override
    public void onCreate() {
        super.onCreate();

        PACKAGE_NAME = getApplicationContext().getPackageName();
    }
}
