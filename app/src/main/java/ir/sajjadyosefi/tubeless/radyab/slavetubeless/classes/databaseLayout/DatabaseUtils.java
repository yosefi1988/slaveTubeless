package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout;

import android.content.Context;
import android.os.Handler;

import com.google.firebase.iid.FirebaseInstanceId;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.Global;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.activity.waitForRegister;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.Setting;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.utility.DeviceUtils;

/**
 * Created by sajjad on 5/10/2018.
 */

public class DatabaseUtils {

    private static DatabaseHelper databaseHelper = null;
    static Dao<Setting, Integer> settingDao;


    public static Setting loadSetting (Context mContext){
        Setting setting = null;

        try {
            settingDao = getHelper(mContext).getTeacherDao();
            // This is how, a reference of DAO object can be done
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

    public static boolean clearMasterSetting( Context mContext) {
        try {
            settingDao = getHelper(mContext).getTeacherDao();
            UpdateBuilder<Setting, Integer> updateBuilder = settingDao.updateBuilder();
            //updateBuilder.where().eq("Id", 1);
            updateBuilder.updateColumnValue("masterPushNotificationToken" /* column */, null /* value */);
            updateBuilder.updateColumnValue("masterPhoneNamber" /* column */, null /* value */);
            updateBuilder.updateColumnValue("isMasterTokenValid" /* column */, false /* value */);
            updateBuilder.update();
            return true;
        }catch (Exception ex){
            return false;
        }
    }


    public static boolean saveMasterToken(Context context, String masterToken, String sender) {
        final Setting setting = new Setting();
        try {
            settingDao = getHelper(context).getTeacherDao();
            UpdateBuilder<Setting, Integer> updateBuilder = settingDao.updateBuilder();
            //updateBuilder.where().eq("Id", 1);
            updateBuilder.updateColumnValue("masterPushNotificationToken" /* column */, masterToken /* value */);
            updateBuilder.updateColumnValue("masterPhoneNamber" /* column */, sender /* value */);
            updateBuilder.updateColumnValue("isMasterTokenValid" /* column */, true /* value */);
            updateBuilder.update();
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public static boolean saveSlaveToken(Context mContext) {
        try {
            settingDao = getHelper(mContext).getTeacherDao();
            UpdateBuilder<Setting, Integer> updateBuilder = settingDao.updateBuilder();
            //updateBuilder.where().eq("Id", 1);
            updateBuilder.updateColumnValue("slavePushNotificationToken" /* column */, Global.token /* value */);
            updateBuilder.updateColumnValue("slavePhoneNumber" /* column */, DeviceUtils.getSimCardOneNumber(mContext) /* value */);
            updateBuilder.updateColumnValue("isSlaveTokenValid" /* column */, true /* value */);
            updateBuilder.update();
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public static Setting initSetting(Context context) {
        // Then, set all the values from user input
        Setting setting = new Setting();
        setting.setMasterPhoneNamber(null);
        setting.setMasterPushNotificationToken("");
        setting.setMasterTokenValid(true);
        setting.setSlavePhoneNumber(DeviceUtils.getSimCardOneNumber(context));
        try {
            setting.setSlavePushNotificationToken(FirebaseInstanceId.getInstance().getToken());
        }catch (Exception ex){
            setting.setSlavePushNotificationToken(null);
        }
        setting.setSlaveTokenValid(true);

        try {
            // This is how, a reference of DAO object can be done
            final Dao<Setting, Integer> settingDao = getHelper(context).getTeacherDao();

            //This is the way to insert data into a database table
            settingDao.create(setting);
        } catch (Exception ex) {

        }
        clean();
        return loadSetting(context);
    }
}
