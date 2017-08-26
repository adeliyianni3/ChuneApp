package com.example.chunetest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Calendar;

/**
 * Created by jeff on 12/2/16.
 * A helper class that knows how to set reminders using the AlarmManager
 */

public class ReminderManager {

    private ReminderManager() {}

    // Parameters needed include String info for notification to pass on
    public static void setReminder(Context context,int id, String reason, Calendar when) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, OnAlarmReciever.class);
        i.putExtra("weatherReason", reason);
        i.putExtra("notificationId", id);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), pi);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, );
    }
}
