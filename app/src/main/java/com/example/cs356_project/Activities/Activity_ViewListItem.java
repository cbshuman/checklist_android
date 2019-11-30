package com.example.cs356_project.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.cs356_project.R;
import com.example.cs356_project.dataModel.CheckListItem;

public class Activity_ViewListItem extends Activity
    {
    public static CheckListItem targetListItem;

    private TimePicker timePicker;

    @Override
    public void onCreate(Bundle savedInstanceState)
        {
        //Start up the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_item);

        //Set the title
        TextView title = findViewById(R.id.viewListItem_title);
        title.setText(targetListItem.GetContents());

        //Setup the reminder checkbox
        final CheckBox reminderBox = findViewById(R.id.viewListItem_reminder);
        reminderBox.setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View v)
                {
                targetListItem.reminder = reminderBox.isChecked();
                }
            });

        //Time picker
        timePicker = findViewById(R.id.viewListItem_timepicker);
        RevealTimePicker();
        }

    private void RevealTimePicker()
        {
        if(targetListItem.reminder)
            {
            timePicker.setVisibility(View.GONE);
            }
        else
            {
            timePicker.setVisibility(View.VISIBLE);
            }
        }

    }
