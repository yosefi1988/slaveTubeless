package ir.sajjadyosefi.tubeless.radyab.slavetubeless.services;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.Global;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.R;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.activity.waitForRegister;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout.DatabaseUtils;

/**
 * Created by sajjad on 12/5/2017.
 */

public class MyFireBaseInstanceIDService extends FirebaseInstanceIdService {


    private static final String TAG = MyFireBaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
//
//        if (refreshedToken!=null) {
//            SettingPreferences.setStringValueInPref(this, SettingPreferences.REG_ID, refreshedToken);
//        }
//        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }
// [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        Global.token = token;
        DatabaseUtils.saveSlaveToken(getApplicationContext());
        if(waitForRegister.textView != null) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    // UI code goes here
                    // Stuff that updates the UI
                    waitForRegister.textView.setText("Token Is OK");
                    waitForRegister.textView.setTextColor(getResources().getColor(R.color.green));
                }
            });


        }
    }

}
