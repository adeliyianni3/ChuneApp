package com.example.chunetest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by jeff on 12/2/16.
 */

public class OnAlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Note: Do not do any asyncronous operations in this method

        NotificationManager mgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent weatherUpdateIntent = new Intent(context, MainActivity.class);
        String reason = intent.getStringExtra("weatherReason");
        int id = intent.getIntExtra("notificationId", 0);
        PendingIntent pi = PendingIntent.getActivity(context, 0, weatherUpdateIntent, PendingIntent.FLAG_ONE_SHOT);

        // Build Notification
        Notification notification = new Notification.Builder(context)
                .setContentTitle("Weather Update")
                .setContentText("Attention " + reason + " approaching")
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        // Send Notification
        mgr.notify(id, notification);
    }
}
