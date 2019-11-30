package com.example.cs356_project.Activities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs356_project.Activities.ViewTools.CheckListAdapter;
import com.example.cs356_project.R;
import com.example.cs356_project.dataModel.CheckListItem;

import java.util.ArrayList;
import java.util.List;

public class CheckListActivity extends Activity
    {
    List<CheckListItem> checkListItems;
    CheckListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_checklist);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        //TODO : Load in the check list here


        checkListItems = new ArrayList<>();
        checkListItems.add(new CheckListItem("giggles","Go to daycare",false));

        adapter = new CheckListAdapter(checkListItems);

        RecyclerView recyclerView = findViewById(R.id.checkbox_recycleView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        Button addButton = findViewById(R.id.addListItemButton);

        addButton.setOnClickListener(new View.OnClickListener()
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
                    }
                }
            });
        }

    public void AddCheckListItem(String content)
        {
        checkListItems.add(new CheckListItem("given", content,false));
        adapter.notifyDataSetChanged();
        }
    }
