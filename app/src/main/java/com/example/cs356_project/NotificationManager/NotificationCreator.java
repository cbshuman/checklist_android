package com.example.cs356_project.NotificationManager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.cs356_project.R;
import com.example.cs356_project.dataModel.CheckListItem;
import com.example.cs356_project.dataModel.UserSettings.UserSettings;

public class NotificationCreator extends BroadcastReceiver
    {
    @Override
    public void onReceive(Context context, Intent intent)
        {
        Intent intent1 = new Intent(context, NotificationIntentService.class);
        context.startService(intent1);
        }

    public void CreateNotification(Context context, CheckListItem targetListItem)
        {

        }
    }
