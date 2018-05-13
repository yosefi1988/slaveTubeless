package ir.sajjadyosefi.tubeless.radyab.slavetubeless.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.businessLayout.RegisterBusiness;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout.DatabaseHelper;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.networkLayout.asyncTask.replyAsyncTask;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.utility.RandomUtil;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.R;

public class waitForRegister extends AppCompatActivity {

    public static Long generateCode;

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




        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
        int permissionReadCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED &&
                permissionReadCheck != PackageManager.PERMISSION_GRANTED  ) {
            ActivityCompat.requestPermissions(((Activity)this),
                    new String[]{
                            android.Manifest.permission.READ_PHONE_STATE,
                            android.Manifest.permission.READ_SMS
                    },
                    9001);
        } else {
            myOnStart();
        }
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

}
