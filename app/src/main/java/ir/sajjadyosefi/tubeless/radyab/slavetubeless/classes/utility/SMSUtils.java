package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.utility;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.ArrayList;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.R;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.businessLayout.RegisterBusiness;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.basic.Sms;


/**
 * Created by sajjad on 5/2/2018.
 */


public class SMSUtils  extends BroadcastReceiver {


    private static final String SENT_SMS_ACTION_NAME = "SMS_SENT";
    private static final String DELIVERED_SMS_ACTION_NAME = "SMS_DELIVERED";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        //Detect l'envoie de sms
        if (intent.getAction().equals(SENT_SMS_ACTION_NAME)) {
            switch (getResultCode()) {
                case Activity.RESULT_OK: // Sms sent
                    Toast.makeText(context, context.getString(R.string.sms_send), Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE: // generic failure
                    Toast.makeText(context, context.getString(R.string.sms_not_send), Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE: // No service
                    Toast.makeText(context, context.getString(R.string.sms_not_send_no_service), Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU: // null pdu
                    Toast.makeText(context, context.getString(R.string.sms_not_send), Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF: //Radio off
                    Toast.makeText(context, context.getString(R.string.sms_not_send_no_radio), Toast.LENGTH_LONG).show();
                    break;
            }
        }
        //detect la reception d'un sms
        else if (intent.getAction().equals(DELIVERED_SMS_ACTION_NAME)) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(context, context.getString(R.string.sms_receive), Toast.LENGTH_LONG).show();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(context, context.getString(R.string.sms_not_receive), Toast.LENGTH_LONG).show();
                    break;
            }
        }else if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                // get sms objects
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus.length == 0) {
                    return;
                }
                // large message might be broken into many
                SmsMessage[] messages = new SmsMessage[pdus.length];
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sb.append(messages[i].getMessageBody());
                }
                String sender = messages[0].getOriginatingAddress();
                String message = sb.toString();


                Toast.makeText(context, sender, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                RegisterBusiness registerBusiness = new RegisterBusiness();
                registerBusiness.replyToMaster(context, sender,message);
                // prevent any other broadcast receivers from receiving broadcast
                // abortBroadcast();
            }
        }
    }


    /**
     * Test if device can send SMS
     * @param context
     * @return
     */
    public static boolean canSendSMS(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
    }

    public static void sendSMS(final Context context,Sms sms) {

        if (!canSendSMS(context)) {
            Toast.makeText(context, context.getString(R.string.cannot_send_sms), Toast.LENGTH_LONG).show();
            return;
        }

        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(SENT_SMS_ACTION_NAME), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED_SMS_ACTION_NAME), 0);

        final SMSUtils smsUtils = new SMSUtils();
        //register for sending and delivery
        context.registerReceiver(smsUtils, new IntentFilter(SMSUtils.SENT_SMS_ACTION_NAME));
        context.registerReceiver(smsUtils, new IntentFilter(DELIVERED_SMS_ACTION_NAME));

        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> parts = smsManager.divideMessage(sms.getMessage());

        ArrayList<PendingIntent> sendList = new ArrayList<>();
        sendList.add(sentPI);

        ArrayList<PendingIntent> deliverList = new ArrayList<>();
        deliverList.add(deliveredPI);

        smsManager.sendMultipartTextMessage(sms.getPhoneNumber(), null, parts, sendList, deliverList);

        //we unsubscribed in 10 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                context.unregisterReceiver(smsUtils);


            }
        }, 10000);

    }
}
