package ir.sajjadyosefi.tubeless.radyab.slavetubeless;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.Setting;

/**
 * Created by sajjad on 5/3/2018.
 */

public class Global extends Application {
    public static String token;

    public static Setting setting = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
