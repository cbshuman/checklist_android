package com.example.cs356_project.NotificationManager;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.cs356_project.Activities.Activity_ViewCheckList;
import com.example.cs356_project.Activities.Activity_ViewListItem;
import com.example.cs356_project.R;
import com.example.cs356_project.dataModel.CheckListItem;
import com.example.cs356_project.dataModel.UserSettings.UserSettings;

import java.util.Random;

public class NotificationCreator extends BroadcastReceiver
    {
    public static int notifiyId = 0;
    @Override
    public void onReceive(Context context, Intent intent)
        {
        System.out.println("Triggering notification: " + (String)intent.getExtras().get("name"));
        CheckListItem targetListItem = Activity_ViewListItem.targetListItem;

        //Make sure our groups are created
        UserSettings.SetupNotificationGroups();

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,new Intent((Activity)UserSettings.GetCurrentActiviy(), Activity_ViewCheckList.class), PendingIntent.FLAG_NO_CREATE);

        //Create notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"Reminders")
                .setSmallIcon(R.drawable.ic_check_black_24dp)
                .setContentTitle(UserSettings.GetNotificationPrefix() + (String)intent.getExtras().get("name"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        Notification notif = builder.build();
        NotificationManager NotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManager.notify(notifiyId,notif);

        notifiyId += 1;
        }
    }
