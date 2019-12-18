package com.example.cs356_project.Activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs356_project.Activities.ViewTools.RecyclerItemTouchHelper;
import com.example.cs356_project.Activities.ViewTools.SubListAdapter;
import com.example.cs356_project.Activities.ViewTools.View_Activity;
import com.example.cs356_project.NotificationManager.NotificationCreator;
import com.example.cs356_project.R;
import com.example.cs356_project.dataModel.CheckListItem;
import com.example.cs356_project.dataModel.Priority;
import com.example.cs356_project.dataModel.SubListItem;
import com.example.cs356_project.dataModel.UserSettings.UserSettings;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class Activity_ViewListItem extends Activity implements View_Activity, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener
    {
    public static CheckListItem targetListItem;

    //GUI elements
    private SubListAdapter adapter;
    private LinearLayout timePicker;
    private TimePicker timeSelection;
    private RelativeLayout timeViewOverlay;
    private RelativeLayout overlayBG;
    private TextView timeView;
    private TextView noSubItems;
    private RecyclerView recyclerView;

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

        //Set the priority
        Spinner prioritySpinner = findViewById(R.id.prioritySpinner);
        prioritySpinner.setSelection(targetListItem.priority.ordinal());
        prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                targetListItem.priority = Priority.values()[position];
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });
        //Setup the reminder checkbox
        final Switch reminderBox = findViewById(R.id.viewListItem_reminder);
        reminderBox.setChecked(targetListItem.reminder);

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

        recyclerView = findViewById(R.id.viewListItem_subList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        // attaching the touch helper to recycler view
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT + ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

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

        UpdateListChange();
        }

    @Override
    public void onPause()
        {
        super.onPause();
        UserSettings.SaveListData();
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

    private void UpdateNotification()
        {
        System.out.println("Getting this notification going . . .");
        Intent newIntent = new Intent(this,NotificationCreator.class);

        newIntent.putExtra("name",targetListItem.GetContents());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,newIntent,0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(UserSettings.GetCurrentDate().GetAsLong());
        calendar.set(Calendar.HOUR_OF_DAY,targetListItem.reminderHour);
        calendar.set(Calendar.MINUTE,targetListItem.reminderMinute);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        long time = 1;
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
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

        UpdateNotification();

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
        if(targetListItem.reminderMinute > 9)
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
        if(targetListItem.subListItems.size() == 0)
            {
            noSubItems.setVisibility(View.VISIBLE);
            }
        else
            {
            noSubItems.setVisibility(View.GONE);
            }
        }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
        {
        if (viewHolder != null)
            {
            // get the removed item name to display it in snack bar
            String name = targetListItem.subListItems.get(viewHolder.getAdapterPosition()).GetContents();

            // backup of removed item for undo purpose
            final SubListItem deletedItem = targetListItem.subListItems.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.RemoveItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar.make(recyclerView, name + " was deleted.", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener()
                {
                @Override
                public void onClick(View view)
                    {
                    // undo is selected, restore the deleted item
                    adapter.RestoreItem(deletedItem, deletedIndex);
                    }
                });
            snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
            snackbar.show();
            }
        }
    }
