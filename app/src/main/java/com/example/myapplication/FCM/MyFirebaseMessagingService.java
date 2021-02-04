package com.example.myapplication.FCM;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getData().isEmpty())
           getMessage(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        else
            getMessage(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"));
    }

    public void getMessage(String title,String msg){
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID = "example.myapplication.FCM.test";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"Notification",NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(msg);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentText(msg)
                .setContentTitle(title);

        manager.notify(new Random().nextInt(),builder.build());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);


    }
}
