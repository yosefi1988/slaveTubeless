package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * Created by sajjad on 5/10/2018.
 */

public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    public static void main(String[] args) throws Exception {
        writeConfigFile("ormlite_config.txt");
    }

//    private static final Class<?>[] classes = new Class[] {
//            SimpleData.class,
//    };

}
