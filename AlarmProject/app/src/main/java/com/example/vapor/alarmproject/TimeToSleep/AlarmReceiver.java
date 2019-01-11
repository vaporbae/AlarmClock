package com.example.vapor.alarmproject.TimeToSleep;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.vapor.alarmproject.R;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setAutoCancel(true).
                setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_alarm_clock)
                .setContentTitle(context.getResources().getString(R.string.alarm_title))
                .setContentText(context.getResources().getString(R.string.alarm_desc))
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setContentInfo("Info")
                .setSound(alarmUri);

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification note=builder.build();
        note.flags = Notification.FLAG_INSISTENT;
        notificationManager.notify(1,note);


    }


}
