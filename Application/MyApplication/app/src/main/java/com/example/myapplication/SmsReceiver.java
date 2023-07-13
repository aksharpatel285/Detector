package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;


public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (bundle != null){
            Object[] sms = (Object[]) bundle.get(SMS_BUNDLE);
            String smsMsg = "";

            for (int i = 0 ; i <sms.length;i++){
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String msgBody = smsMessage.getMessageBody().toString();
                String msgAddress = smsMessage.getOriginatingAddress().toString();

                smsMsg += "\n"+msgAddress+"\n";
                smsMsg += msgBody+"\n\n";

            }
            MainActivity inst = MainActivity.Instance();
            inst.updateList(smsMsg);
        }
    }
}
