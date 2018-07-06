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
import android.util.Log;
import android.widget.Toast;

import com.androidfung.geoip.IpApiService;
import com.androidfung.geoip.ServicesManager;
import com.androidfung.geoip.model.GeoIpResponseModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.Global;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.AppLocationService;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.Sensors.AddressListener;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.asyncTask.ReplyServiceRequestAsyncTask;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout.DatabaseUtils;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.pushNotification.PushDataJson;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.pushNotification.PushObject;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response.ResponseObject;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response.ResponseToken;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response.ServerStatus;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.services.RequestService;
import retrofit2.Call;
import retrofit2.Callback;

import static ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.services.RequestService.SERVICE_ADDRESS_DEFAULT;
import static ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.services.RequestService.SERVICE_GEO_DEFAULT;

/**
 * Created by sajjad on 5/1/2018.
 */

public class GpsBusiness {

    public void handleNotifications(final Context mContext, String data) {
        PushDataJson pushData = new PushDataJson();
//        Global.setting = DatabaseUtils.loadSetting(mContext);


        Gson gson = new Gson();
        pushData = gson.fromJson(data, PushDataJson.class);

        //OK
        if (pushData.getMessage().getServiceType() == SERVICE_GEO_DEFAULT) {
            getLastGeo(mContext,SERVICE_GEO_DEFAULT);
        }
        //OK
        if (pushData.getMessage().getServiceType() == RequestService.SERVICE_ADDRESS_DEFAULT) {
            getLastAddress(mContext,RequestService.SERVICE_ADDRESS_DEFAULT);
        }
        if (pushData.getMessage().getServiceType() == RequestService.SERVICE_GEONETWORK) {
            getLastGeoByNetwork(mContext);
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



    private static void getLastGeoByNetwork(final Context context) {
        final LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        final AppLocationService appLocationService;
        appLocationService = new AppLocationService(context);

        boolean isNetEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //return true;
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {

                @Override
                public void run() {
                    Location nwLocation = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);

                    if (nwLocation != null) {
                        double latitude = nwLocation.getLatitude();
                        double longitude = nwLocation.getLongitude();
                        Toast.makeText(context,
                                "Mobile Location (NW): \nLatitude: " + latitude
                                        + "\nLongitude: " + longitude,
                                Toast.LENGTH_LONG).show();
                    } else {
//                        showSettingsAlert("NETWORK");
                        Toast.makeText(context,
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
        } else
            return null;
    }


    private static void getLastAddress0(final Context context) {
        final LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

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
                                                " (countryCode : " + countryCode + ")" +
                                                " (timezone : " + timezone + ")" +
                                                " city : " + city +
                                                //" state : " + state +
                                                " region : " + region +
                                                " isp : " + isp;// +
//                                                " postalCode : " + postalCode +
//                                                " knownName : " + knownName;
                            } else {
                                address = "address : null";
                            }
//                            ReplyServiceRequestAsyncTask replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context, RequestService.SERVICE_ADDRESS_DEFAULT, address);
//                            replyServiceRequestAsyncTask.execute();


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
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "gps not enable", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    public List<Address> getGeocoderAddress(Context context) {
        if (mCurrentLocation != null)
        {
            Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
            try
            {
                List<Address> addresses = geocoder.getFromLocation(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), 1);
                return addresses;
            }
            catch (IOException e)
            {
                //e.printStackTrace();
                Log.e("Error : Geocoder", "Impossible to connect to Geocoder", e);
            }
        }

        return null;
    }

    private String getAddress(final Context context, Location location) {


        try {
            String fullAddress = null;
            Geocoder gcd = new Geocoder(context);
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

        int a = 5;
        a++;

        return json;
    }

    public String createResponseJson(int type, String radioStatus) {
        //not ok
        Gson gson = new Gson();
        ResponseObject responseGeo = new ResponseObject();
        responseGeo.setType(type);
//        responseToken.setSlavePushNotificationToken(Global.setting.getSlavePushNotificationToken());
//        responseToken.serverStatus.setCode(0);

        responseGeo.serverStatus = new ServerStatus();
        responseGeo.serverStatus.setMessage(radioStatus);
        responseGeo.setObject(null);

        PushObject pushObject = new PushObject();

        if(Global.setting != null)
            pushObject.setTo(Global.setting.getMasterPushNotificationToken());

        pushObject.data.setMessage(gson.toJson(responseGeo));//responseToken
        pushObject.time = System.currentTimeMillis() + "";

        String json = gson.toJson(pushObject);
        int a = 5;
        a++;
        return json;
    }
    public String createResponseJson(int type, Location location) {
        //ok
        Gson gson = new Gson();

        ResponseObject responseGeo = new ResponseObject();
        responseGeo.setType(type);
//        responseToken.setSlavePushNotificationToken(Global.setting.getSlavePushNotificationToken());
//        responseToken.serverStatus.setCode(0);

        responseGeo.serverStatus = new ServerStatus();
        responseGeo.serverStatus.setMessage("ok");
        responseGeo.setObject(gson.toJson(location));


        PushObject pushObject = new PushObject();

        if(Global.setting != null)
            pushObject.setTo(Global.setting.getMasterPushNotificationToken());

        pushObject.data.setMessage(gson.toJson(responseGeo));//responseToken
        pushObject.time = System.currentTimeMillis() + "";

        String json = gson.toJson(pushObject);
        int a = 5;
        a++;

        return json;
    }




    //////////////////////////////////////// location gps ////////////////////////////////////////////////

    // location last updated time
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 100;

    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;
    String OutPut ;
    String OutPutLastUpdate ;
    boolean itsOK = false;

    private void getLastGeo(final Context context,int type) {

        if(isGPSAvailable(context)) {
            init(context,type);

            if (mCurrentLocation != null) {
                //Send To Master
                ReplyServiceRequestAsyncTask replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context, type, null, mCurrentLocation);
                replyServiceRequestAsyncTask.execute();

//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(context, "Lat: " + mCurrentLocation.getLatitude() + ", Lng: " + mCurrentLocation.getLongitude(), Toast.LENGTH_LONG).show();
//                }
//            });
            } else {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ReplyServiceRequestAsyncTask replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context, SERVICE_GEO_DEFAULT, "location permission has been deny", null);
                    replyServiceRequestAsyncTask.execute();
                }else {
                    mRequestingLocationUpdates = true;
                    startLocationUpdates(context,type);
                }
            }
        }else {
            ReplyServiceRequestAsyncTask replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context, SERVICE_GEO_DEFAULT, "GPS provider not Enable", null);
            replyServiceRequestAsyncTask.execute();
        }
    }

    private void startLocationUpdates(final Context context, final int type) {


        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback,Looper.getMainLooper());
                updateLocationUI(context,type);

            }
        });

    }

    private boolean isGPSAvailable(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void init(final Context context, final int type) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mSettingsClient = LocationServices.getSettingsClient(context);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI(context,type);
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }
    private void updateLocationUI(Context context,int type) {
        if (mCurrentLocation != null) {
            OutPut = "Lat: " + mCurrentLocation.getLatitude() + ", " + "Lng: " + mCurrentLocation.getLongitude();
            OutPutLastUpdate = "Last updated on: " + mLastUpdateTime;
            itsOK = true;

            ReplyServiceRequestAsyncTask replyServiceRequestAsyncTask;
            if(type == SERVICE_GEO_DEFAULT)
                replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context, SERVICE_GEO_DEFAULT,null ,mCurrentLocation);
            else {
                String address = getAddress(context,mCurrentLocation);
                List<Address> listAddress = getGeocoderAddress(context);
                replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context, SERVICE_ADDRESS_DEFAULT,null ,address);
            }
            replyServiceRequestAsyncTask.execute();
        }
        if (itsOK){
            //stop
            mFusedLocationClient.removeLocationUpdates(mLocationCallback) ;
            itsOK = false;
        }
    }


    //////////////////////////////////////// location address ////////////////////////////////////////////////

    private void getLastAddress(final Context context,int type) {

        if(isGPSAvailable(context)) {
            init(context,type);

            if (mCurrentLocation != null) {
                //Send To Master

                String address = getAddress(context ,mCurrentLocation);
                List<Address> listAddress = getGeocoderAddress(context);

                ReplyServiceRequestAsyncTask replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context, type, null, address);
                replyServiceRequestAsyncTask.execute();

//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(context, "Lat: " + mCurrentLocation.getLatitude() + ", Lng: " + mCurrentLocation.getLongitude(), Toast.LENGTH_LONG).show();
//                }
//            });
            } else {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ReplyServiceRequestAsyncTask replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context, RequestService.SERVICE_ADDRESS_DEFAULT, "location permission has been deny", null);
                    replyServiceRequestAsyncTask.execute();
                }else {
                    mRequestingLocationUpdates = true;
                    startLocationUpdates(context,type);
                }
            }
        }else {
            ReplyServiceRequestAsyncTask replyServiceRequestAsyncTask = new ReplyServiceRequestAsyncTask(context, SERVICE_GEO_DEFAULT, "GPS provider not Enable", null);
            replyServiceRequestAsyncTask.execute();
        }
    }

}
