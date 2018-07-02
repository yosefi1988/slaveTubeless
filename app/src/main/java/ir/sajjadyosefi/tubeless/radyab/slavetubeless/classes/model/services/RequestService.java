package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.services;

/**
 * Created by sajjad on 6/4/2018.
 */

public class RequestService {
    public static final int SERVICE_TYPE      = 0;
    public static final int SERVICE_GEO_DEFAULT = 1;
    public static final int SERVICE_ADDRESS_DEFAULT = 3;


    public static final int BATTERY_LEVEL     = 2;
    public static final int SERVICE_GEONETWORK       = 4;


    public static final int Reserve          = 10;


    int ServiceType ;


    public int getServiceType() {
        return ServiceType;
    }

    public void setServiceType(int serviceType) {
        ServiceType = serviceType;
    }
}
