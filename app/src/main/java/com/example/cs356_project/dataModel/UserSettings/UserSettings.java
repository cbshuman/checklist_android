package com.example.cs356_project.dataModel.UserSettings;

import android.graphics.Color;

import com.example.cs356_project.R;
import com.example.cs356_project.dataModel.CheckListItem;

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

    public static List<CheckListItem> checkListItems;



    public void SetUserSettings()
        {
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
    }
