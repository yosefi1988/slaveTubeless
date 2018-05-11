package ir.sajjadyosefi.tubeless.radyab.slavetubeless.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_register);

        RandomUtil random = new RandomUtil();
        generateCode = random.generateRandom(5);

        TextView textView = (TextView) findViewById(R.id.textViewRandomCode);
        textView.setText(generateCode.toString());


        RegisterBusiness registerBusiness = new RegisterBusiness();
        registerBusiness.replyToMaster(this, "+989382764218","RND" + generateCode.toString()+"d9OxzA-GFb4:APA91bGgjUimcZTHbjp5lv0YEiu7rBK-rPQmSWZr0GbgdSo1YFRM7LP7iY0qr52aE4Sc3LY78td3wdaw2ZrZh9oDfpTvUaWAc3obpJ2jV2NBrNeutfVwpTVJmZU1FH3Ow1aI5aDqeDsY");

    }


}
