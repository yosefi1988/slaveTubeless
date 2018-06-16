package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.businessLayout;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.Global;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.Sensors.AddressListener;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.asyncTask.ReplyServiceRequestAsyncTask;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout.DatabaseUtils;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.pushNotification.PushDataJson;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.pushNotification.PushObject;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response.ResponseToken;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.services.RequestService;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by sajjad on 5/1/2018.
 */

public class RadyabBusiness {
    public static final int TYPE_TOKEN = 10;

    public static String TMP = null;
    static LocationManager mLocationManager;
    static Location myLocation = null;

    public static void handleNotifications(final Context context, String data) {
        PushDataJson pushData = new PushDataJson();

        Gson gson = new Gson();
        pushData = gson.fromJson(data, PushDataJson.class);

        if (pushData.getMessage().getServiceType() == RequestService.SERVICE_GEO) {
            getLastGeo(context);
        }
        if (pushData.getMessage().getServiceType() == RequestService.SERVICE_ADDRESS) {
            getLastAddress(context);
        } else if (pushData.getMessage().getServiceType() == RequestService.BATTERY_LEVEL) {

        }
        if (pushData.getMessage().getServiceType() == RequestService.SERVICE_TYPE) {

        }

        int a = 5;
        a++;
    }

    private static void getLastAddress(final Context context) {
        final LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,new AddressListener(context));
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                Toast.makeText(context, getAddress(context,location) , Toast.LENGTH_LONG).show();
                String address = getAddress(context,location);
                ReplyServiceRequestAsyncTask replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context, RequestService.SERVICE_ADDRESS, address);
                replyServiceRequestAsyncTask.execute();
            }
        });
    }
    private static void getLastGeo(final Context context) {
        final LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,new AddressListener(context));
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                ReplyServiceRequestAsyncTask replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context, RequestService.SERVICE_GEO, location);
                replyServiceRequestAsyncTask.execute();
            }
        });
    }


    private static String getAddress(final Context context,Location location) {
        try {
            String fullAddress = null;
            Geocoder gcd = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

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
                                " knownName : " + knownName;
            }
            String s = "My Current City is: " + fullAddress;

            return s;
        } catch (Exception e) {
            return null;
        }
    }




    public static Location getLastKnownLocation(Context context) {


        mLocationManager = (LocationManager) context.getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    public String createResponseJsonADDRESS(int type, String address) {
//        Global.setting = DatabaseUtils.loadSetting(mContext);

        Gson gson = new Gson();

        ResponseToken responseToken = new ResponseToken();
        responseToken.setType(type);
//        responseToken.setSlavePushNotificationToken(Global.setting.getSlavePushNotificationToken());
//        responseToken.serverStatus.setCode(0);
        responseToken.serverStatus.setMessage(address.toString());

        PushObject pushObject = new PushObject();
        //TODO master token
//        pushObject.setTo(Global.setting.getMasterPushNotificationToken());
        pushObject.setTo("czuvcycXdOs:APA91bGowemr3BCJaKSeCWQ6-a18oHwGHd4Y7hMY5lzSCa2Scb7W5SRJk-JzFS6wuurPi8KmmspuB6x8qAGl_LJA-3DjSiZBuFasg8cKpMzs81fvjVZKgWrIM5rXBoOLcIMHTSXzqAaD");
        pushObject.data.setMessage(gson.toJson(responseToken));//responseToken

        String json = gson.toJson(pushObject);

        int a = 5 ;
        a++;

        return json;
    }

    public String createResponseJsonGEO(int type, Location location) {

        Gson gson = new Gson();

        ResponseToken responseToken = new ResponseToken();
        responseToken.setType(type);
//        responseToken.setSlavePushNotificationToken(Global.setting.getSlavePushNotificationToken());
//        responseToken.serverStatus.setCode(0);
        responseToken.serverStatus.setMessage(location.toString());

        PushObject pushObject = new PushObject();
        pushObject.setTo(Global.setting.getMasterPushNotificationToken());
        pushObject.setTo("czuvcycXdOs:APA91bGowemr3BCJaKSeCWQ6-a18oHwGHd4Y7hMY5lzSCa2Scb7W5SRJk-JzFS6wuurPi8KmmspuB6x8qAGl_LJA-3DjSiZBuFasg8cKpMzs81fvjVZKgWrIM5rXBoOLcIMHTSXzqAaD");

        pushObject.data.setMessage(gson.toJson(responseToken));//responseToken
        String json = gson.toJson(pushObject);
        String sssssssss = "{\"to\": \"" + Global.setting.masterPushNotificationToken + "\",\"data\": {\"message\": \"" + "json----" + Global.setting.getSlavePushNotificationToken() + "----json" + "\"}}";
        int a = 5;
        a++;

        return json;
    }

}
