package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.utility;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

/**
 * Created by sajjad on 5/10/2018.
 */

public class DeviceUtils {

    public static String getSimCardOneNumber(Context mContext) {
        try {
            TelephonyManager tMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                String mPhoneNumber = tMgr.getLine1Number();
                return mPhoneNumber;
            }
            return null;
        }catch (Exception ex){
            return "+98912";
        }
    }

}
