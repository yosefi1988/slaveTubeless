package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.asyncTask;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.Toast;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.Global;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.businessLayout.GpsBusiness;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.businessLayout.RegisterBusiness;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.basic.Sms;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.basic.BasicObject;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response.GooglePushResponse;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.networkLayout.HttpUtils;

import static ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.services.RequestService.SERVICE_GPS;

/**
 * Created by sajjad on 5/7/2018.
 */

public class ReplyServiceRequestAsyncTask extends AsyncTask {

    private Context mContext;
    private int serviceType ;
    private String address ;
    private Location location ;


    private String messageToResoinse;

//    public ReplyServiceRequestAsyncTask(Context mContext, int serviceAddress, String address) {
//        this.mContext = mContext;
//        this.serviceType = serviceAddress;
//        this.address = address;
//    }


    public ReplyServiceRequestAsyncTask(Context mContext, int serviceType, String radioStatus, Object object) {
        init(mContext, serviceType, radioStatus, object);
    }

    private void init(Context mContext, int serviceType, String radioStatus, Object object) {
        this.mContext = mContext;
        this.serviceType = serviceType;

        if(serviceType == 0){
            RegisterBusiness registerBusiness = new RegisterBusiness();
            messageToResoinse = registerBusiness.createResponseJson();
        }else {
            GpsBusiness gpsBusiness = new GpsBusiness();
            if (serviceType == SERVICE_GPS){
                if(radioStatus == null){
                    location = (Location) object;
                    messageToResoinse = gpsBusiness.createResponseJson(serviceType, location);

                }else {
                    messageToResoinse = gpsBusiness.createResponseJson(serviceType, radioStatus);
                }
            }
        }
    }

    public ReplyServiceRequestAsyncTask(Context context, int serviceType) {
        init(context, serviceType, null, null);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
                if(results.error !=null && results.error.length() >1)
                    Toast.makeText(mContext, results.error, Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}