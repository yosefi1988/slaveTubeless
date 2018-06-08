package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.Sensors;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static ir.sajjadyosefi.tubeless.radyab.slavetubeless.activity.waitForRegister.textViewGeo;

/**
 * Created by sajjad on 6/8/2018.
 */

public class AddressListener implements LocationListener {

    private static final String TAG = "sajjad";
    private final Context context;

    public AddressListener(Context context ) {
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location loc) {
//        textViewGeo.setText("");
//        pb.setVisibility(View.INVISIBLE);
        Toast.makeText(context, "Location changed: Lat: " + loc.getLatitude() + " Lng: "  + loc.getLongitude(), Toast.LENGTH_SHORT).show();
        String longitude = "Longitude: " + loc.getLongitude();
        Log.v(TAG, longitude);
        String latitude = "Latitude: " + loc.getLatitude();
        Log.v(TAG, latitude);

        /*------- To get city name from coordinates -------- */
        String fullAddress = null;
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);

            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                fullAddress =
                        "address : " + address +
                        " city : " + city +
                        " state : " + state +
                        " country : " + country +
                        " postalCode : " + postalCode +
                        " knownName : " + knownName ;
            }

            String s = "My Current City is: " + fullAddress;
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//            textViewGeo.setText(s);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        int a = 0 ;
        a++;
    }

    @Override
    public void onProviderEnabled(String provider) {
        int a = 0 ;
        a++;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        int a = 0 ;
        a++;
    }
}
