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
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.Global;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.R;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.Sensors.AddressListener;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout.DatabaseUtils;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.utility.RandomUtil;

public class testActivity extends AppCompatActivity {


    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        context = this;

    }

}
