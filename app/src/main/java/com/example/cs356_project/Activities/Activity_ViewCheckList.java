package com.example.cs356_project.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs356_project.Activities.ViewTools.CheckListAdapter;
import com.example.cs356_project.Activities.ViewTools.View_Activity;
import com.example.cs356_project.R;
import com.example.cs356_project.Serialization.Serializer;
import com.example.cs356_project.dataModel.CheckListItem;
import com.example.cs356_project.dataModel.Date;
import com.example.cs356_project.dataModel.UserSettings.UserSettings;

import java.util.ArrayList;

public class Activity_ViewCheckList extends Activity implements View_Activity
    {
    //GUI stuff
    private RelativeLayout overlayBG;
    private RelativeLayout calendarOverlay;
    private CalendarView calendarView;
    private TextView dateTitle;

    CheckListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        UserSettings.SetCurrentActivity(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_checklist);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        //Set the current date on the calendar and title
        dateTitle = findViewById(R.id.list_date);
        dateTitle.setText(UserSettings.GetCurrentDate().toString());

        calendarView = findViewById(R.id.list_calenderView);
        calendarView.setDate(UserSettings.GetCurrentDate().GetAsLong());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
            {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
                {
                ChangeCurrentDate(year,month,dayOfMonth);
                ShowCalendar(false);
                }
            });

        //Overlay
        overlayBG = findViewById(R.id.list_overlay);

        adapter = new CheckListAdapter(UserSettings.checkListItems);

        RecyclerView recyclerView = findViewById(R.id.checkbox_recycleView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        //Add button
        findViewById(R.id.addListItemButton).setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View v)
                {
                //Get the text and clear it
                EditText editText = findViewById(R.id.addListItemContent);
                String currentText = editText.getText().toString();

                //Check that we actually have something to look at
                if(!currentText.equals(""))
                    {
                    editText.setText("");
                    AddCheckListItem(currentText);
                    UpdateListChange();
                    }
                }
            });

        //Calendar stuff
        calendarOverlay = findViewById(R.id.list_calendar);
        ShowCalendar(false);
        findViewById(R.id.list_dateIcon).setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View v)
                {
                ShowCalendar(true);
                }
            });

        findViewById(R.id.list_calendar_cancel).setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View v)
                {
                ShowCalendar(false);
                }
            });
        }

    private void SetUp()
        {

        }

    protected void ShowCalendar(boolean input)
        {
        if(input)
            {
            overlayBG.setVisibility(View.VISIBLE);
            calendarOverlay.setVisibility(View.VISIBLE);
            }
        else
            {
            overlayBG.setVisibility(View.GONE);
            calendarOverlay.setVisibility(View.GONE);
            }
        }

    @Override
    protected void onResume()
        {
        super.onResume();
        adapter.notifyDataSetChanged();
        }

    public void AddCheckListItem(String content)
        {
        UserSettings.checkListItems.add(new CheckListItem("given", content,false));
        UpdateListChange();
        UserSettings.SaveListData();
        }

    protected void ChangeCurrentDate(int year, int month, int dayOfMonth)
        {
        UserSettings.SetNewDate(year,month,dayOfMonth);

        UpdateList();
        }

    protected void UpdateList()
        {
        dateTitle.setText(UserSettings.GetCurrentDate().toString());
        UpdateListChange();
        }

    @Override
    public void UpdateListChange()
        {
        adapter.UpdateList(UserSettings.checkListItems);
        }
    }
