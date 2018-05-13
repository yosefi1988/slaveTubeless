package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.businessLayout;

import android.content.Context;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout.DatabaseUtils;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.Global;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.activity.waitForRegister;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.Sms;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.networkLayout.asyncTask.replyAsyncTask;

/**
 * Created by sajjad on 5/3/2018.
 */

public class RegisterBusiness {

    private Context mContext = null;

    public void replyToMaster(Context context, String sender, String message) {

        this.mContext = context;

        if (checkMessageRandomCode(message))
            if (DatabaseUtils.saveMasterTokenToDatabase(context, message.substring(8), sender)) {
                (new replyAsyncTask(context)).execute();
            }
    }



    private boolean checkMessageRandomCode( String message) {
        if (message.substring(0,3).equals("RND")){
            if (message.substring(3,8).equals(waitForRegister.generateCode.toString())){
                return true;
            }else
                return false;

        }else
            return false;
    }

    public String createResponseJson(){
        Global.setting = DatabaseUtils.loadSetting(mContext);

        return  "{\"to\": \""+ Global.setting.masterPushNotificationToken +"\",\"data\": {\"message\": \""+ "json----" +Global.setting.getSlavePushNotificationToken()+ "----json" +"\"}}";
    }

}
