package com.example.cs356_project.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs356_project.Activities.ViewTools.SubListAdapter;
import com.example.cs356_project.R;
import com.example.cs356_project.dataModel.CheckListItem;

public class Activity_ViewListItem extends Activity
    {
    public static CheckListItem targetListItem;

    private SubListAdapter adapter;
    private LinearLayout timePicker;

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
                RevealTimePicker();
                }
            });

        //Time picker
        timePicker = findViewById(R.id.viewListItem_timepicker_view);
        RevealTimePicker();

        //Sublist
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new SubListAdapter(targetListItem.subListItems);

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
                if(!addContext.equals(""))
                    {
                    targetListItem.AddSubListItem(addContext.getText().toString());
                    addContext.setText("");
                    }
                adapter.notifyDataSetChanged();
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

    }
