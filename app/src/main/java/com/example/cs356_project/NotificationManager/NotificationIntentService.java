package com.example.cs356_project.NotificationManager;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.cs356_project.R;
import com.example.cs356_project.dataModel.CheckListItem;
import com.example.cs356_project.dataModel.UserSettings.UserSettings;

public class NotificationIntentService extends IntentService
    {
    private Context context;
    private CheckListItem targetListItem;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * @param name Used to name the worker thread, important only for debugging.
     * @param context
     * @param targetListItem
     */
    public NotificationIntentService(String name, Context context, CheckListItem targetListItem)
        {
        super(name);
        this.context = context;
        this.targetListItem = targetListItem;
        }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
        {
        //Create notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"test")
                .setSmallIcon(R.drawable.ic_check_black_24dp)
                .setContentTitle(UserSettings.GetNotificationPrefix() + targetListItem.GetContents())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notif = builder.build();
        NotificationManager NotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManager.notify(001,notif);
        }
    }
