package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.Sensors;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by sajjad on 6/8/2018.
 */

public class TubelessLocationListener implements android.location.LocationListener {

    private static final String TAG = "sajjad";
    private final Context context;

    public TubelessLocationListener(Context context) {
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location loc) {
//        pb.setVisibility(View.INVISIBLE);
        Toast.makeText(context, "Location changed: Lat: " + loc.getLatitude() + " Lng: "  + loc.getLongitude(), Toast.LENGTH_SHORT).show();
        String longitude = "Longitude: " + loc.getLongitude();
        Log.v(TAG, longitude);
        String latitude = "Latitude: " + loc.getLatitude();
        Log.v(TAG, latitude);
        String s = longitude + "\n" + latitude + "\n " ;

    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
