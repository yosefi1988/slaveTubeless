package ir.sajjadyosefi.tubeless.radyab.slavetubeless.services;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.businessLayout.RadyabBusiness.handleNotifications;

/**
 * Created by sajjad on 12/5/2017.
 */

public class GCMNotificationIntentService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        Log.d("Zero TO Hero", "Sender :" + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d("Zero TO Hero", "Data :" + remoteMessage.getData());
            handleNotifications(getApplicationContext(),remoteMessage.getData().toString());
        }
        if (remoteMessage.getNotification() != null) {
            Log.d("Zero TO Hero", "MessageGetService :" + remoteMessage.getNotification().getBody());
            Log.d("Zero TO Hero", "Title  :" + remoteMessage.getNotification().getTitle());
        }
        //Toast.makeText(getApplicationContext(),"Get Notif",Toast.LENGTH_LONG).show();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(GCMNotificationIntentService.this.getApplicationContext(),
                        remoteMessage.getData().toString()
                        ,Toast.LENGTH_SHORT).show();
            }
        });
    }

}