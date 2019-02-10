package com.example.vapor.alarmproject.WakeMeUpHere;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.vapor.alarmproject.R;

public class ProximityIntentReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {

        String key = LocationManager.KEY_PROXIMITY_ENTERING;

        Boolean entering = intent.getBooleanExtra(key, false);

        if (entering) {
            Log.d(getClass().getSimpleName(), "entering");
        }
        else {
            Log.d(getClass().getSimpleName(), "exiting");
        }

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        Notification notification = createNotification();

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setAutoCancel(true).
                setDefaults(Notification.DEFAULT_ALL) .
                setSmallIcon(R.mipmap.ic_place)
                .setContentTitle(context.getResources().getString(R.string.arrived_at_dest_title))
                .setContentText(context.getResources().getString(R.string.arrived_at_dest_desc))
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setContentInfo("Info")
                .setSound(alarmUri);

        notification = builder.build();
        notification.flags = Notification.FLAG_INSISTENT;
        notificationManager.notify(1,notification);

    }

    private Notification createNotification() {
        Notification notification = new Notification();

        notification.icon = R.mipmap.ic_launcher_round;
        notification.when = System.currentTimeMillis();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;

        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;

        notification.ledARGB = Color.WHITE;
        notification.ledOnMS = 1500;
        notification.ledOffMS = 1500;

        return notification;
    }

}
