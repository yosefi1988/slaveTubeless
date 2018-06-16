package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.businessLayout;

import android.content.Context;

import com.google.gson.Gson;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.asyncTask.ReplyServiceRequestAsyncTask;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout.DatabaseUtils;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.Global;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.activity.waitForRegister;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.pushNotification.PushObject;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response.ResponseToken;

/**
 * Created by sajjad on 5/3/2018.
 */

public class RegisterBusiness {

    private Context mContext = null;

    public void replyToMaster(Context context, String sender, String message) {

        this.mContext = context;

        if (checkMessageRandomCode(message))
            if (DatabaseUtils.saveMasterToken(context, message.substring(8), sender)) {
                (new ReplyServiceRequestAsyncTask(context,0,"")).execute();
            }
    }



    private boolean checkMessageRandomCode( String message) {
        if(message != null) {
            if (message.substring(0, 3).equals("RND")) {
                if (message.substring(3, 8).equals(waitForRegister.generateCode.toString())) {
                    return true;
                } else
                    return false;

            } else
                return false;
        }
        else
            return false;
    }

    public String createResponseJson(){
        Global.setting = DatabaseUtils.loadSetting(mContext);


        Gson gson = new Gson();
        ResponseToken responseToken = new ResponseToken();
        responseToken.setType(10);
        responseToken.setSlavePushNotificationToken(Global.setting.getSlavePushNotificationToken());
        responseToken.serverStatus.setCode(0);
        responseToken.serverStatus.setMessage("ok");
        PushObject pushObject = new PushObject();
        pushObject.setTo(Global.setting.getMasterPushNotificationToken());
        pushObject.setTo("czuvcycXdOs:APA91bGowemr3BCJaKSeCWQ6-a18oHwGHd4Y7hMY5lzSCa2Scb7W5SRJk-JzFS6wuurPi8KmmspuB6x8qAGl_LJA-3DjSiZBuFasg8cKpMzs81fvjVZKgWrIM5rXBoOLcIMHTSXzqAaD");

        pushObject.data.setMessage(gson.toJson(responseToken));//responseToken

        String json = gson.toJson(pushObject);


        String sssssssss =  "{\"to\": \""+ Global.setting.masterPushNotificationToken +"\",\"data\": {\"message\": \""+ "json----" +Global.setting.getSlavePushNotificationToken()+ "----json" +"\"}}";

        int a = 5 ;
        a++;

        return json;
    }

}
