package ir.sajjadyosefi.tubeless.radyab.slavetubeless.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.Global;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.businessLayout.RegisterBusiness;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout.DatabaseHelper;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout.DatabaseUtils;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response.GooglePushResponse;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.networkLayout.HttpUtils;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.networkLayout.asyncTask.replyAsyncTask;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.utility.RandomUtil;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.R;

public class waitForRegister extends AppCompatActivity {

    public static Long generateCode;
    Context context;


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 9001:
                if ((grantResults.length > 0) ){
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        myOnStart();
                    }else {
                        //Toast.makeText(context,context.getString(R.string.WeNeedYourDeviceInfo),Toast.LENGTH_LONG).show();
                    }
                }else {

                }
                break;

            default:
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_register);


        checkRegistered();
        checkTakedToken();
        context = this;

//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
//            public void run() {
//
//            }
//        });
        if(isNetworkOnline(context)){
            Runnable runnable = new Runnable() {
                public void run() {
                    String test = "{\"data\":{\"message\":\"{\\\"slavePushNotificationToken\\\":\\\"eNWUjDiHF4o:APA91bETNGt4slw7HSPODtvfmQHJkyiyBwRciJZ5wh63m7QKllxIi3qYUSeAt4yCo7kBetn7wdS_hSr2Wh40jX9Vc_OAp-rjnWMFc1h1Ci2zB9pYRv6prkFCpjOEosW4AQzWQUROtvRF\\\",\\\"serverStatus\\\":{\\\"Code\\\":0,\\\"Message\\\":\\\"ok\\\"}}\"},\"to\":\"crvdz6-gZYg:APA91bEtFQEg2SANboccxaH-VV0P-DZQPJiTdEE4QKE2GX12eZCbnynAjNkGIAM-6uPJSdiTKSXgE16huP28_mKK-Pyieze7VKc7hmvDSvwffX-9n4NxEC_eMvg5P4bd_GW5mvDsSBZo\"}";
                    HttpUtils.PostRequestToFCM(context,test, GooglePushResponse.class);
                }
            };
            Thread mythread = new Thread(runnable);
            mythread.start();
        }



        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
        int permissionReadCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        int permissionReadCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int permissionReadCheck3 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED &&
                permissionReadCheck != PackageManager.PERMISSION_GRANTED &&
                permissionReadCheck2 != PackageManager.PERMISSION_GRANTED  &&
                permissionReadCheck3 != PackageManager.PERMISSION_GRANTED  ) {
            ActivityCompat.requestPermissions(((Activity)this),
                    new String[]{
                            android.Manifest.permission.READ_PHONE_STATE,
                            android.Manifest.permission.READ_SMS,
                            android.Manifest.permission.INTERNET,
                            android.Manifest.permission.ACCESS_NETWORK_STATE,
                    },
                    9001);
        } else {
            myOnStart();
        }
    }

    public static TextView textView;
    private void checkTakedToken() {
        Global.setting = DatabaseUtils.loadSetting(this);
        textView =  ((TextView)findViewById(R.id.textViewTokenStatus));
        if(Global.setting == null)
            Global.setting = DatabaseUtils.initSetting(this);

        if(Global.setting.getSlavePushNotificationToken() == null) {
            textView.setText("Token Is Not OK");
            textView.setTextColor(getResources().getColor(R.color.red));
        }else {
            textView.setText("Token Is OK");
            textView.setTextColor(getResources().getColor(R.color.green));
        }
    }

    private void checkRegistered() {
        Global.setting = DatabaseUtils.loadSetting(this);
        if(Global.setting  != null && Global.setting.getMasterPhoneNamber() != null){
            this.startActivity(new Intent(this,MainActivity.class));
        }else
            DatabaseUtils.clearMasterSetting(this);
    }

    private void myOnStart() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {

                    RandomUtil random = new RandomUtil();
                    generateCode = random.generateRandom(5);

                    TextView textView = (TextView) findViewById(R.id.textViewRandomCode);
                    textView.setText(generateCode.toString());



                } catch ( Exception e) {
                    e.printStackTrace();
                }

            }
        },150);
    }
    public static boolean isNetworkOnline(Context con)
    {
        boolean status = false;
        try
        {
            ConnectivityManager cm = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);

            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);

                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                    status = true;
                } else {
                    status = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return status;
    }
}
