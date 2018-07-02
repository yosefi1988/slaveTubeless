package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.asyncTask;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.Toast;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.Global;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.businessLayout.RadyabBusiness;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.businessLayout.RegisterBusiness;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout.DatabaseUtils;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.Sms;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.basic.BasicObject;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response.GooglePushResponse;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.services.RequestService;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.networkLayout.HttpUtils;

/**
 * Created by sajjad on 5/7/2018.
 */

public class ReplyServiceRequestAsyncTask extends AsyncTask {

    private Context mContext;
    private int serviceType ;
    private String address ;
    private Location location ;


    private String messageToResoinse;

    public ReplyServiceRequestAsyncTask(Context mContext, int serviceAddress, String address) {
        this.mContext = mContext;
        this.serviceType = serviceAddress;
        this.address = address;
    }

    public ReplyServiceRequestAsyncTask(Context mContext, int serviceAddress, Location loc) {
        this.mContext = mContext;
        this.serviceType = serviceAddress;
        this.location = loc;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(serviceType == 0){
            RegisterBusiness registerBusiness = new RegisterBusiness();
            messageToResoinse = registerBusiness.createResponseJson();
        }else {
            RadyabBusiness radyabBusiness = new RadyabBusiness();
            if (serviceType == RequestService.SERVICE_GEO_DEFAULT) {
                Global.setting = DatabaseUtils.loadSetting(mContext);
                messageToResoinse = radyabBusiness.createResponseJsonGEO(serviceType, location);
            } else if (serviceType == RequestService.SERVICE_ADDRESS_DEFAULT) {
                messageToResoinse = radyabBusiness.createResponseJsonADDRESS(serviceType, address);
            }
        }
    }

    @Override
    protected GooglePushResponse doInBackground(Object[] objects) {
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