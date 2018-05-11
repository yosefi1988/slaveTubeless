package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout;

import android.content.Context;
import android.os.Handler;

import com.google.firebase.iid.FirebaseInstanceId;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.util.List;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.Setting;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.utility.DeviceUtils;

/**
 * Created by sajjad on 5/10/2018.
 */

public class DatabaseUtils {

    private static DatabaseHelper databaseHelper = null;

    public static boolean saveMasterTokenToDatabase(Context context, String masterToken, String sender) {
        final Setting setting = new Setting();

        // Then, set all the values from user input
        setting.setMasterPhoneNamber(sender);
        setting.setMasterPushNotificationToken(masterToken);
        setting.setMasterTokenValid(true);
        setting.setSlavePhoneNumber(DeviceUtils.getSimCardOneNumber(context));
        setting.setSlavePushNotificationToken(FirebaseInstanceId.getInstance().getToken());
        setting.setSlaveTokenValid(true);

        try {
            // This is how, a reference of DAO object can be done
            final Dao<Setting, Integer> settingDao = getHelper(context).getTeacherDao();

            //This is the way to insert data into a database table
            settingDao.create(setting);
        } catch (Exception ex) {

        }
        clean();
        return true;
    }

    public static Setting loadSetting (Context mContext){
        Setting setting = null;


        try {
            // This is how, a reference of DAO object can be done
            final Dao<Setting, Integer> settingDao = getHelper(mContext).getTeacherDao();
            List<Setting> settingList = settingDao.queryForAll();

            if(settingList.size() == 1)
                setting = settingList.get(0);
            else
                setting = null;


        } catch (Exception ex) {

        }
        finally {

            clean();
            return setting;
        }
    }

    // This is how, DatabaseHelper can be initialized for future use
    private static DatabaseHelper getHelper(Context mContext) {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(mContext, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    public static void clean(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (databaseHelper != null) {
                    OpenHelperManager.releaseHelper();
                    databaseHelper = null;
                }
            }
        }, 10000);
    }

}
