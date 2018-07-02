package ir.sajjadyosefi.tubeless.radyab.slavetubeless.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.Global;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.Sensors.AddressListener;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.Sensors.GPSTracker;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.businessLayout.RadyabBusiness;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout.DatabaseUtils;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response.GooglePushResponse;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.networkLayout.HttpUtils;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.utility.RandomUtil;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.R;

public class waitForRegister extends AppCompatActivity {

    private static final String TAG = "ssssssssssss";
    public static Long generateCode;
    Context context;
    public static TextView textViewGeo;


    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager locationManager;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 9001:
                if ((grantResults.length > 0)) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        myOnStart();
                    } else {
                        //Toast.makeText(context,context.getString(R.string.WeNeedYourDeviceInfo),Toast.LENGTH_LONG).show();
                    }
                } else {

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


        //1
//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
//            public void run() {
//
//            }
//        });

//        2
//        if(isNetworkOnline(context)){
//            Runnable runnable = new Runnable() {
//                public void run() {
//                    String test = "{\"data\":{\"message\":\"{\\\"Type\\\":10,\\\"slavePushNotificationToken\\\":\\\"d4pzpJarggQ:APA91bGhBmVtXFo7SukqbC26Cjfd0ZHK7Wji_unzSSpk9-pStTK5sll3fdLrQrOOUabcn6PqQP1MDgE0aANZp8v2pUWaI-BVLGbVzNYJWF6pS3Js8YNb-VAkIFqT1OOGIRoVvILJkhCG25KGv_-B6sR3qLmPKrnwCg\\\",\\\"serverStatus\\\":{\\\"Code\\\":0,\\\"MessageGetService\\\":\\\"ok\\\"}}\"},\"to\":\"d4pzpJarggQ:APA91bGhBmVtXFo7SukqbC26Cjfd0ZHK7Wji_unzSSpk9-pStTK5sll3fdLrQrOOUabcn6PqQP1MDgE0aANZp8v2pUWaI-BVLGbVzNYJWF6pS3Js8YNb-VAkIFqT1OOGIRoVvILJkhCG25KGv_-B6sR3qLmPKrnwCg\"}";
//                    HttpUtils.PostRequestToFCM(context,test, GooglePushResponse.class);
//                }
//            };
//            Thread mythread = new Thread(runnable);
//            mythread.start();
//        }


        //textViewGeo = (TextView) findViewById(R.id.textViewGeo);
        //3
//        String sssssssssss = "{message={\"ServiceType\":3}}";//1
//        handleNotifications(getApplicationContext(),sssssssssss);








        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
        int permissionReadCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        int permissionReadCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int permissionReadCheck3 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);
        int permissionReadCheck4 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED &&
                permissionReadCheck != PackageManager.PERMISSION_GRANTED &&
                permissionReadCheck2 != PackageManager.PERMISSION_GRANTED  &&
                permissionReadCheck4 != PackageManager.PERMISSION_GRANTED  &&
                permissionReadCheck3 != PackageManager.PERMISSION_GRANTED  ) {
            ActivityCompat.requestPermissions(((Activity)this),
                    new String[]{
                            android.Manifest.permission.READ_PHONE_STATE,
                            android.Manifest.permission.READ_SMS,
                            android.Manifest.permission.INTERNET,
                            android.Manifest.permission.ACCESS_NETWORK_STATE,
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                    },
                    9001);
        } else {
            myOnStart();
        }


        //this.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//        GPSTracker gpsTracker = new GPSTracker(context);
//        Location aaaaaaaaaaaa = gpsTracker.getLocation();
//        int aa = 0;
//        aa++;


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
//        Global.setting = DatabaseUtils.loadSetting(this);
//        if(Global.setting  != null && Global.setting.getMasterPhoneNamber() != null){
//            this.startActivity(new Intent(this,MainActivity.class));
//        }else
//            DatabaseUtils.clearMasterSetting(this);
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
