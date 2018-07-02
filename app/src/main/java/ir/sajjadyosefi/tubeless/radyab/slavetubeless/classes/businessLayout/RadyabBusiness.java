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

import com.androidfung.geoip.IpApiService;
import com.androidfung.geoip.ServicesManager;
import com.androidfung.geoip.model.GeoIpResponseModel;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.Global;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.AppLocationService;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.Sensors.AddressListener;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.asyncTask.ReplyServiceRequestAsyncTask;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.pushNotification.PushDataJson;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.pushNotification.PushObject;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response.ResponseGeo;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response.ResponseToken;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.services.RequestService;
import retrofit2.Call;
import retrofit2.Callback;

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

        //OK
        if (pushData.getMessage().getServiceType() == RequestService.SERVICE_GEO_DEFAULT) {
            getLastGeo(context);
        }
        //OK
        if (pushData.getMessage().getServiceType() == RequestService.SERVICE_ADDRESS_DEFAULT) {
            getLastAddress(context);
        }
        if (pushData.getMessage().getServiceType() == RequestService.SERVICE_GEONETWORK) {
            getLastGeoByNetwork(context);
        } else if (pushData.getMessage().getServiceType() == RequestService.BATTERY_LEVEL) {

        }
        if (pushData.getMessage().getServiceType() == RequestService.SERVICE_TYPE) {

        }

        int a = 5;
        a++;
    }

//    public static boolean hasProviders() {
//        LocationManager locationManager = (LocationManager) TiApplication
//                .getInstance().getApplicationContext()
//                .getSystemService(TiApplication.LOCATION_SERVICE);
//        boolean Enabled = (locationManager
//                .isProviderEnabled(LocationManager.GPS_PROVIDER)
//                || locationManager
//                .isProviderEnabled(LocationManager.PASSIVE_PROVIDER) || locationManager
//                .isProviderEnabled(LocationManager.NETWORK_PROVIDER));
//        locationManager = null;
//        return Enabled;
//    }

    private boolean isGPSAvailable(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    private static void getLastGeoByNetwork(final Context context) {
        final LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        final AppLocationService appLocationService;
        appLocationService = new AppLocationService(context);

        boolean isNetEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //return true;
        }else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {

                @Override
                public void run() {
                    Location nwLocation = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);

                    if (nwLocation != null) {
                        double latitude = nwLocation.getLatitude();
                        double longitude = nwLocation.getLongitude();
                        Toast.makeText( context,
                                "Mobile Location (NW): \nLatitude: " + latitude
                                        + "\nLongitude: " + longitude,
                                Toast.LENGTH_LONG).show();
                    } else {
//                        showSettingsAlert("NETWORK");
                        Toast.makeText( context,
                                "Mobile Location (NW): \nLatitude: null" + "\nLongitude: null",
                                Toast.LENGTH_LONG).show();
                    }

                }
            });



        }
    }
    private static Location getLastGeoByNetwork0(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location network = null;
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return network;
            }
            network = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            return network;
        }else
            return null;
    }

    private static void getLastAddress(final Context context) {
        final LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new AddressListener(context));
                    final Location[] location = {lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)};

                    //1
                    //force location
                    IpApiService ipApiService = ServicesManager.getGeoIpService();
                    ipApiService.getGeoIp().enqueue(new Callback<GeoIpResponseModel>() {
                        @Override
                        public void onResponse(Call<GeoIpResponseModel> call, retrofit2.Response<GeoIpResponseModel> response) {
                            String country = response.body().getCountry();
                            String city = response.body().getCity();
                            String countryCode = response.body().getCountryCode();
                            double latitude = response.body().getLatitude();
                            double longtidue = response.body().getLongitude();
                            String region = response.body().getRegion();
                            String timezone = response.body().getTimezone();
                            String isp = response.body().getIsp();

                            location[0] = new Location("Auto");

                            location[0].setLatitude(latitude);
                            location[0].setLongitude(longtidue);


                            String address;
                            if (location != null) {
                                //address = getAddress(context, location[0]);

                                address =
                                        "country : " + country +
                                                " (countryCode : " + countryCode + ")"+
                                                " (timezone : " + timezone + ")"+
                                                " city : " + city +
                                                //" state : " + state +
                                                " region : " + region +
                                                " isp : " + isp;// +
//                                                " postalCode : " + postalCode +
//                                                " knownName : " + knownName;
                            }else {
                                address = "address : null";
                            }
                            ReplyServiceRequestAsyncTask replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context, RequestService.SERVICE_ADDRESS_DEFAULT, address);
                            replyServiceRequestAsyncTask.execute();


                        }

                        @Override
                        public void onFailure(Call<GeoIpResponseModel> call, Throwable t) {
                            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });


                    //2
//                    getLocation(context);

//                    ReplyServiceRequestAsyncTask replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context, RequestService.SERVICE_GEO_DEFAULT, location);
//                    replyServiceRequestAsyncTask.execute();
                }
            });
        }else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,"gps not enable" ,Toast.LENGTH_LONG).show();
                }
            });

        }
    }
    private static void getLastGeo(final Context context) {
        final LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new AddressListener(context));
                    final Location[] location = {lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)};

                    //1
                    //force location
                    IpApiService ipApiService = ServicesManager.getGeoIpService();
                    ipApiService.getGeoIp().enqueue(new Callback<GeoIpResponseModel>() {
                        @Override
                        public void onResponse(Call<GeoIpResponseModel> call, retrofit2.Response<GeoIpResponseModel> response) {
                            String country = response.body().getCountry();
                            String city = response.body().getCity();
                            String countryCode = response.body().getCountryCode();
                            double latitude = response.body().getLatitude();
                            double longtidue = response.body().getLongitude();
                            String region = response.body().getRegion();
                            String timezone = response.body().getTimezone();
                            String isp = response.body().getIsp();

                            location[0] = new Location("Auto");

                            location[0].setLatitude(latitude);
                            location[0].setLongitude(longtidue);
                            ReplyServiceRequestAsyncTask replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context, RequestService.SERVICE_GEO_DEFAULT, location[0]);
                            replyServiceRequestAsyncTask.execute();
                        }

                        @Override
                        public void onFailure(Call<GeoIpResponseModel> call, Throwable t) {
                            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });


                    //2
//                    getLocation(context);

//                    ReplyServiceRequestAsyncTask replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context, RequestService.SERVICE_GEO_DEFAULT, location);
//                    replyServiceRequestAsyncTask.execute();
                }
            });
        }else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,"gps not enable" ,Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private static void getLocation(Context context) {

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
        pushObject.setTo(Global.setting.getMasterPushNotificationToken());
        pushObject.data.setMessage(gson.toJson(responseToken));//responseToken

        String json = gson.toJson(pushObject);

        int a = 5 ;
        a++;

        return json;
    }

    public String createResponseJsonGEO(int type, Location location) {

        Gson gson = new Gson();

        ResponseGeo responseGeo = new ResponseGeo();
        responseGeo.setType(type);
//        responseToken.setSlavePushNotificationToken(Global.setting.getSlavePushNotificationToken());
//        responseToken.serverStatus.setCode(0);

        if(location != null) {
            responseGeo.serverStatus.setMessage("ok");
        }else {
            responseGeo.serverStatus.setMessage("location = null");
        }

        responseGeo.setGeo(location.toString());
        PushObject pushObject = new PushObject();
        pushObject.setTo(Global.setting.getMasterPushNotificationToken());

        pushObject.data.setMessage(gson.toJson(responseGeo));//responseToken
        String json = gson.toJson(pushObject);
        String sssssssss = "{\"to\": \"" + Global.setting.masterPushNotificationToken + "\",\"data\": {\"message\": \"" + "json----" + Global.setting.getSlavePushNotificationToken() + "----json" + "\"}}";
        int a = 5;
        a++;

        return json;
    }

}
