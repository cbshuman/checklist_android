package com.example.cs356_project.dataModel.UserSettings;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

import com.example.cs356_project.Activities.ViewTools.View_Activity;
import com.example.cs356_project.R;
import com.example.cs356_project.Serialization.Serializer;
import com.example.cs356_project.dataModel.CheckListItem;
import com.example.cs356_project.dataModel.CheckListItemSorter;
import com.example.cs356_project.dataModel.Date;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserSettings
    {
    public static final int sound_congrats_pro =  R.raw.complete_professional;
    public static final int sound_congrats_fun =  R.raw.funcompletion;

    public static final int sound_smallCongrats_pro = R.raw.small_professional;

    private static int sound_congrats = sound_congrats_pro;
    private static int sound_minorCongrats = sound_smallCongrats_pro;

    private static Color primary;
    private static Color secondary;

    //All the data relating to the current list
    public static List<CheckListItem> checkListItems;
    private static View_Activity currentActivity;
    private static Date currentListDate;

    public static CharSequence GetNotificationPrefix()
        {
        return ("Just a reminder: ");
        }

    public void SetUserSettings()
        {
        }

    public static void SetupNotificationGroups()
        {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
            CharSequence name = "Reminders";
            String description = "Reminders for to-do items";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Reminders", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = ((Activity)currentActivity).getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            }
        }

    public static void SetNewDate(int year, int month, int dayOfMonth)
        {
        SaveListData();

        currentListDate = new Date(year,month,dayOfMonth);

        LoadListData();

        UpdateList();
        }

    public static Date GetCurrentDate()
        {
        if(currentListDate == null)
            {
            //Gets the current date
            currentListDate = new Date();
            LoadListData();
            }

        return(currentListDate);
        }

    private static void LoadListData()
        {
        //System.out.println("Checking if " + currentListDate.toString() + " exists: " + Serializer.CheckIfFileExists(currentListDate.toString(),(Activity)currentActivity));

        if(Serializer.CheckIfFileExists(currentListDate.toString(),(Activity)currentActivity))
            {
            System.out.println("Tryin to load. . .");
            String loadedData = Serializer.ReadFromFile(currentListDate.toString(),(Activity)currentActivity);
            //Load it up
            Type listType = new TypeToken<ArrayList<CheckListItem>>(){}.getType();
            checkListItems = new Gson().fromJson(loadedData,listType);

            for (CheckListItem i: checkListItems)
                {
                i.VerifyChildren();
                }
            //System.out.println("Got a list : " + (checkListItems != null));
            }
        else
            {
            //System.out.println("Building a brand new list");
            checkListItems = new ArrayList<>();
            }
        }

    public static void SaveListData()
        {
        String jsonListData = new Gson().toJson(checkListItems);
        System.out.println("Saving:");
        System.out.println(jsonListData);
        Serializer.WriteToFile(currentListDate.toString(),jsonListData, (Activity)currentActivity);
        }

    public static Color getPrimary()
        {
        return primary;
        }

    public static Color getSecondary()
        {
        return secondary;
        }

    public static int getSound_congrats()
        {
        return sound_congrats;
        }

    public static int getSound_minorCongrats()
        {
        return(sound_minorCongrats);
        }

    public static void UpdateList()
        {
        Collections.sort(checkListItems,new CheckListItemSorter());
        currentActivity.UpdateListChange();
        }

    public static void SetCurrentActivity(View_Activity activity)
        {
        currentActivity = activity;
        }

    public static View_Activity GetCurrentActiviy()
        {
        return(currentActivity);
        }
    }
