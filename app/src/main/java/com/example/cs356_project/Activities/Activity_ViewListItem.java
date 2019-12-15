package com.example.cs356_project.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs356_project.Activities.ViewTools.SubListAdapter;
import com.example.cs356_project.Activities.ViewTools.View_Activity;
import com.example.cs356_project.R;
import com.example.cs356_project.dataModel.CheckListItem;
import com.example.cs356_project.dataModel.UserSettings.UserSettings;

public class Activity_ViewListItem extends Activity implements View_Activity
    {
    public static CheckListItem targetListItem;

    private SubListAdapter adapter;
    private LinearLayout timePicker;
    private TimePicker timeSelection;
    private RelativeLayout timeViewOverlay;
    private RelativeLayout overlayBG;
    private TextView timeView;
    private TextView noSubItems;

    @Override
    public void onCreate(Bundle savedInstanceState)
        {
        UserSettings.SetCurrentActivity(this);

        //Start up the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_item);

        //Set the title
        TextView title = findViewById(R.id.viewListItem_title);
        title.setText("Todo: " + targetListItem.GetContents());

        //Setup the reminder checkbox
        final Switch reminderBox = findViewById(R.id.viewListItem_reminder);
        reminderBox.setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View v)
                {
                targetListItem.reminder = reminderBox.isChecked();
                RevealTimePicker();
                }
            });

        //Overlay to hide buttons
        overlayBG = findViewById(R.id.viewListItem_overlay);

        //Time picker
        timePicker = findViewById(R.id.viewListItem_timepicker_view);
        timeSelection = findViewById(R.id.viewListItem_timepicker);
        timeView = findViewById(R.id.viewListItem_timepicker_time);

        //Update the gui
        timeSelection.setCurrentMinute(targetListItem.reminderMinute);
        timeSelection.setCurrentHour(targetListItem.reminderHour);
        RevealTimePicker();
        //Update the time
        UpdateReminderTime();

        timeViewOverlay = findViewById(R.id.viewListItem_clock);
        ShowClock(false);

        //Set up cancel button
        findViewById(R.id.viewListItem_timepick_cancel).setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View v)
                {
                ShowClock(false);
                }
            });

        //Set up okay button
        findViewById(R.id.viewListItem_timepick_okay).setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View v)
                {
                SetReminderTime();
                }
            });

        //Sublist
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new SubListAdapter(targetListItem.subListItems);

        noSubItems = findViewById(R.id.viewListItem_nosublist);

        RecyclerView recyclerView = findViewById(R.id.viewListItem_subList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        //Add text
        final EditText addContext = findViewById(R.id.viewListItem_addText);

        //Add Button
        Button addNewSubItem = findViewById(R.id.viewListItem_addSub);
        addNewSubItem.setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View v)
                {
                if(!addContext.getText().toString().equals(""))
                    {
                    targetListItem.AddNewSubListItem(addContext.getText().toString());
                    UpdateListChange();
                    addContext.setText("");
                    }
                adapter.notifyDataSetChanged();
                }
            });

        timePicker.setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View v)
                {
                ShowClock(true);
                }
            });
        }

    private void RevealTimePicker()
        {
        if(!targetListItem.reminder)
            {
            timePicker.setVisibility(View.GONE);
            }
        else
            {
            timePicker.setVisibility(View.VISIBLE);
            }
        }

    private void ShowClock(boolean selectClock)
        {
        if(selectClock)
            {
            overlayBG.setVisibility(View.VISIBLE);
            timeViewOverlay.setVisibility(View.VISIBLE);
            }
        else
            {
            overlayBG.setVisibility(View.GONE);
            timeViewOverlay.setVisibility(View.GONE);
            }
        }

    private void SetReminderTime()
        {
        ShowClock(false);

        targetListItem.reminderHour = timeSelection.getCurrentHour();
        targetListItem.reminderMinute = timeSelection.getCurrentMinute();

        UpdateReminderTime();
        }

    private void UpdateReminderTime()
        {
        StringBuilder timeToString = new StringBuilder();

        //Hours
        if(targetListItem.reminderHour == 0 || targetListItem.reminderHour == 12)
            {
            timeToString.append(12);
            }
        else
            {
            timeToString.append(targetListItem.reminderHour%12);
            }

        timeToString.append(":");

        //Minutes
        if(targetListItem.reminderMinute > 10)
            {
            timeToString.append(targetListItem.reminderMinute);
            }
        else
            {
            timeToString.append("0" + targetListItem.reminderMinute);
            }

        //AM/PM
        if(targetListItem.reminderHour >= 12 && targetListItem.reminderHour != 0)
            {
            timeToString.append(" pm");
            }
        else
            {
            timeToString.append(" am");
            }

        timeView.setText(timeToString.toString());
        }

    @Override
    public void UpdateListChange()
        {
        adapter.notifyDataSetChanged();
        if(targetListItem.subListItems.size() > 0)
            {
            noSubItems.setVisibility(View.GONE);
            }
        else
            {
            noSubItems.setVisibility(View.VISIBLE);
            }
        }
    }
