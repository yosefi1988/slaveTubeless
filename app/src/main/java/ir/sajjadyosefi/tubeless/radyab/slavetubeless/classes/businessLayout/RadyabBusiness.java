package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.businessLayout;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;

import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.Global;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.Sensors.TubelessLocationListener;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.asyncTask.ReplyServiceRequestAsyncTask;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout.DatabaseUtils;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.pushNotification.PushDataJson;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.pushNotification.PushObject;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response.ResponseToken;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.services.RequestService;

/**
 * Created by sajjad on 5/1/2018.
 */

public class RadyabBusiness {
    public static final int TYPE_TOKEN = 10;

    public static String TMP = null;

    public static void handleNotifications(final Context context, String data) {
        PushDataJson pushData = new PushDataJson();

        Gson gson = new Gson();
        pushData = gson.fromJson(data, PushDataJson.class);

        if (pushData.getMessage().getServiceType() == RequestService.SERVICE_GEO) {
            Location loc = getLastGeo(context);
            ReplyServiceRequestAsyncTask replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context,RequestService.SERVICE_ADDRESS,loc);
            replyServiceRequestAsyncTask.execute();
        }
        if (pushData.getMessage().getServiceType() == RequestService.SERVICE_ADDRESS) {
            String address = getAddress(context);
            ReplyServiceRequestAsyncTask replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context,RequestService.SERVICE_ADDRESS,address);
            replyServiceRequestAsyncTask.execute();

        } else if (pushData.getMessage().getServiceType() == RequestService.BATTERY_LEVEL) {

        }
        if (pushData.getMessage().getServiceType() == RequestService.SERVICE_TYPE) {

        }

        int a = 5;
        a++;
    }

//    private static Location getGeo(final Context context) {
//        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        final android.location.LocationListener locationListener = new TubelessLocationListener(context);
//
//        new Thread() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
//                Looper.loop();
//            }
//        }.start();
//    }

    private static Location getLastGeo(final Context context) {
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location aaaaa = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return aaaaa;
    }


    private static String getAddress(final Context context) {
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        final android.location.LocationListener locationListener = new TubelessLocationListener(context);
//
//        new Thread() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
//                Looper.loop();
//            }
//        }.start();


//        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }



            Location aaaaa = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            String fullAddress = null;
            Geocoder gcd = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(aaaaa.getLatitude(), aaaaa.getLongitude(), 1);

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

            return s;
        }catch (Exception e) {
            return null;
        }
    }


    public String createResponseJson( Context mContext,Location location) {
        Global.setting = DatabaseUtils.loadSetting(mContext);

        Gson gson = new Gson();
        ResponseToken responseToken = new ResponseToken();
        responseToken.setType(10);
        responseToken.setSlavePushNotificationToken(Global.setting.getSlavePushNotificationToken());
        responseToken.serverStatus.setCode(0);
        responseToken.serverStatus.setMessage(location.toString());
        PushObject pushObject = new PushObject();
        pushObject.setTo(Global.setting.getMasterPushNotificationToken());
        pushObject.data.setMessage(gson.toJson(responseToken));//responseToken

        String json = gson.toJson(pushObject);


        String sssssssss =  "{\"to\": \""+ Global.setting.masterPushNotificationToken +"\",\"data\": {\"message\": \""+ "json----" +Global.setting.getSlavePushNotificationToken()+ "----json" +"\"}}";

        int a = 5 ;
        a++;

        return json;
    }

    public String createResponseJson( Context mContext,String address) {
//        Global.setting = DatabaseUtils.loadSetting(mContext);

        Gson gson = new Gson();
        ResponseToken responseToken = new ResponseToken();
        responseToken.setType(10);
        responseToken.setSlavePushNotificationToken(Global.setting.getSlavePushNotificationToken());
        responseToken.serverStatus.setCode(0);
        responseToken.serverStatus.setMessage(address.toString());
        PushObject pushObject = new PushObject();
        //TODO master token
//        pushObject.setTo(Global.setting.getMasterPushNotificationToken());
        pushObject.setTo("czuvcycXdOs:APA91bGowemr3BCJaKSeCWQ6-a18oHwGHd4Y7hMY5lzSCa2Scb7W5SRJk-JzFS6wuurPi8KmmspuB6x8qAGl_LJA-3DjSiZBuFasg8cKpMzs81fvjVZKgWrIM5rXBoOLcIMHTSXzqAaD");
        pushObject.data.setMessage(gson.toJson(responseToken));//responseToken

        String json = gson.toJson(pushObject);


        String sssssssss =  "{\"to\": \""+ Global.setting.masterPushNotificationToken +"\",\"data\": {\"message\": \""+ "json----" +Global.setting.getSlavePushNotificationToken()+ "----json" +"\"}}";

        int a = 5 ;
        a++;

        return json;
    }
}
