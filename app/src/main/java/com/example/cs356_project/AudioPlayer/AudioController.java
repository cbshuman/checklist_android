package com.example.cs356_project.AudioPlayer;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.cs356_project.R;
import com.example.cs356_project.dataModel.UserSettings.UserSettings;

public class AudioController
    {
    public static void PlayCongrats(Context context)
        {
        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(context, UserSettings.getSound_congrats());
        mediaPlayer.start();
        }
    }
