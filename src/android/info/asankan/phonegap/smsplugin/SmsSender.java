package info.asankan.phonegap.smsplugin;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;

import java.util.ArrayList;

/**
 * Created by Asanka on 12/16/13.
 */
public class SmsSender {
    private Activity activity;

    public SmsSender(Activity activity){
        this.activity=activity;
    }

    public void invokeSMSIntent(String phoneNumber, String message) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", message);
        sendIntent.setType("vnd.android-dir/mms-sms");
        activity.startActivity(sendIntent);
    }

    public void sendSMS(String phoneNumber, String message) {
        /*
        SmsManager manager = SmsManager.getDefault();
        PendingIntent sentIntent = PendingIntent.getActivity(activity, 0, new Intent(), 0);
        PendingIntent deliveryIntent=PendingIntent.getActivity(activity,0,new Intent(),0);
        manager.sendTextMessage(phoneNumber, null, message, sentIntent, null);
        */

        message= "GP: "+ message;

        PendingIntent mSendIntent=     PendingIntent.getActivity(activity, 0 , new Intent(), 0);
        PendingIntent mDeliveryIntent= PendingIntent.getActivity(activity, 0 , new Intent(), 0);

        
        SmsManager sm = SmsManager.getDefault();
        ArrayList<String> parts =sm.divideMessage(message);
        int numParts = parts.size();

        ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();

        for (int i = 0; i < numParts; i++) {
        sentIntents.add(PendingIntent.getBroadcast(getContext(), 0, mSendIntent, 0));
        deliveryIntents.add(PendingIntent.getBroadcast(getContext(), 0, mDeliveryIntent, 0));
        }

        sm.sendMultiPartTextMessage(phoneNumber,null, parts, sentIntents, deliveryIntents);
    }
}
