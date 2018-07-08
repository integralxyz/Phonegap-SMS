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

        SmsManager sms = SmsManager.getDefault();
    
        ArrayList<String> messages = sms.divideMessage(message);
        int messageCount = messages.size();
        ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>(messageCount);
        ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>(messageCount);

        for (int j = 0; j < messageCount; j++) {
            sentIntents.add( PendingIntent.getBroadcast( activity , 0 , new Intent( ) , 0 ) );
        }
        sms.sendMultipartTextMessage(phoneNumber, null, messages, sentIntents, null);
    }
}
