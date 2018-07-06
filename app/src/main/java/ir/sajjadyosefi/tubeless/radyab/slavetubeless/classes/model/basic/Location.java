package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.basic;

/**
 * Created by sajjad on 7/6/2018.
 */

public class Location {
    double latitude ;
    double longitude ;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public void copy(android.location.Location location) {
        this.setLatitude(location.getLatitude());
        this.setLongitude(location.getLongitude());
    }
}
