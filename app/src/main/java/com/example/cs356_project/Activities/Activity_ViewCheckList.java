package com.example.cs356_project.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs356_project.Activities.ViewTools.CheckListAdapter;
import com.example.cs356_project.Activities.ViewTools.RecyclerItemTouchHelper;
import com.example.cs356_project.Activities.ViewTools.View_Activity;
import com.example.cs356_project.R;
import com.example.cs356_project.dataModel.CheckListItem;
import com.example.cs356_project.dataModel.Date;
import com.example.cs356_project.dataModel.UserSettings.UserSettings;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class Activity_ViewCheckList extends Activity implements View_Activity, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener
    {
    //GUI stuff
    private RelativeLayout overlayBG;
    private RelativeLayout calendarOverlay;
    private CalendarView calendarView;
    private TextView dateTitle;
    private RecyclerView recyclerView;
    private Date date;
    private CheckListAdapter adapter;

    private final static String[] hints  = {
                                            "Did you know?\nThat you can set a todo on a date in advance!",
                                            "Did you know?\nYou can set a notification for todo items!",
                                            "Did you know?\nYou can add sub-tasks to a todo!",
                                            "Did you know?\nYou can order your tasks by priority!"
                                            };

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        //Setting the user settings
        UserSettings.SetCurrentActivity(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_checklist);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        //Welcome
        int rand =  new Random().nextInt((hints.length));

        final View welcomeParent = findViewById(R.id.list_welcome_p);
        TextView welcomeText = findViewById(R.id.list_welcome_text);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
            {
            public void run()
                {
                Animation fadeout = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadeout1);
                welcomeParent.setAnimation(fadeout);
                welcomeParent.animate();
                welcomeParent.setVisibility(View.GONE);
                }
            }, 2000);

        System.out.println(hints[rand]);
        welcomeText.setText(hints[rand]);

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

        recyclerView = findViewById(R.id.checkbox_recycleView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        // attaching the touch helper to recycler view
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT + ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

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
                    UpdateList();
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

    @Override
    protected void onResume()
        {
        super.onResume();
        UserSettings.SetCurrentActivity(this);
        UserSettings.UpdateList();
        }

    @Override
    public void onPause()
        {
        super.onPause();
        UserSettings.SaveListData();
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

    public void AddCheckListItem(String content)
        {
        UserSettings.checkListItems.add(new CheckListItem("given", content,false));
        System.out.println(UserSettings.checkListItems);
        UserSettings.UpdateList();
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
        adapter.notifyDataSetChanged();
        }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
        {
        if (viewHolder != null)
            {
            // get the removed item name to display it in snack bar
            String name = UserSettings.checkListItems.get(viewHolder.getAdapterPosition()).GetContents();

            // backup of removed item for undo purpose
            final CheckListItem deletedItem = UserSettings.checkListItems.get(viewHolder.getAdapterPosition());
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
