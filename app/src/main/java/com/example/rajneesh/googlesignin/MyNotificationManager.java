package com.example.rajneesh.googlesignin;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.solver.widgets.Helper;
import android.support.v4.app.NotificationCompat;

import com.example.rajneesh.googlesignin.Activities.Main2Activity;

public class MyNotificationManager {
    private Context mCtx;
    private static MyNotificationManager mInsatnce;

    private  MyNotificationManager(Context context){
        mCtx =context;

    }

    public static synchronized MyNotificationManager getMyInsatnce(Context context){

        if(mInsatnce==null){
            mInsatnce=new MyNotificationManager(context);
        }
        return mInsatnce;


    }


    public void displayNotification(String title,String body){

        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(mCtx, com.example.rajneesh.googlesignin.Helper.CHANNEL_ID)
                .setSmallIcon(android.support.compat.R.drawable.notification_icon_background)
                .setContentTitle(title)
                .setContentText(body);

        Intent intent=new Intent(mCtx, Main2Activity.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(mCtx,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotificationManager= (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel mChannel= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(com.example.rajneesh.googlesignin.Helper.CHANNEL_ID, com.example.rajneesh.googlesignin.Helper.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

            mChannel.setDescription(com.example.rajneesh.googlesignin.Helper.CHANNEL_DESCRPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,300,200,100});
            mNotificationManager.createNotificationChannel(mChannel);
        }




        if (mNotificationManager!=null){

            mNotificationManager.notify(1,mBuilder.build());
        }


    }

}
