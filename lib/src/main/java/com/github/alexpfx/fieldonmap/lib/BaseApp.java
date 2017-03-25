package com.github.alexpfx.fieldonmap.lib;

import android.app.Application;
import android.os.StrictMode;

/**
 * Created by alexandre on 24/03/2017.
 */

public class BaseApp extends Application{

        @Override
        public void onCreate() {

            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
            super.onCreate();
        }

}
