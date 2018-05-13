package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.networkLayout.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.Global;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.businessLayout.RegisterBusiness;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout.DatabaseUtils;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.BasicObject;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.Sms;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.push.GooglePushResponse;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.push.PushObject;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.networkLayout.HttpUtils;

/**
 * Created by sajjad on 5/7/2018.
 */

public class replyAsyncTask extends AsyncTask {

    private Context mContext;
    private String messageToResoinse;

    public replyAsyncTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        RegisterBusiness registerBusiness = new RegisterBusiness();
        messageToResoinse = registerBusiness.createResponseJson();
    }

    @Override
    protected GooglePushResponse doInBackground(Object[] objects) {
        PushObject pushObject = new PushObject();
        pushObject.data = "ssssssss";
        pushObject.to = Global.setting.getMasterPushNotificationToken();
        Gson gson = new Gson();
        String json = gson.toJson(pushObject);
        GooglePushResponse googlePushResponse = HttpUtils.PostRequestToFCM(mContext,messageToResoinse,BasicObject.class);

        return googlePushResponse;
    }

    public Sms CreateSmsForSendToClient(String sender) {

        Sms sms = new Sms();
        sms.setPhoneNumber(sender);

        //install firebase
        String message = "TS" + Global.token;
        sms.setMessage(message);
        return sms;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        GooglePushResponse googlePushResponse = (GooglePushResponse) o;

        try {
            for (GooglePushResponse.Results results : googlePushResponse.getResults()) {
                Toast.makeText(mContext, results.error, Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}