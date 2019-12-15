package com.example.cs356_project.dataModel;

import android.widget.CheckBox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckListItem extends Note implements Serializable
    {
    private boolean completed;
    public boolean reminder = false;
    public int reminderHour;
    public int reminderMinute;
    public Priority priority;
    public ArrayList<SubListItem> subListItems;

    public CheckListItem(String id,String contents, boolean completed)
        {
        super(id,contents);

        this.completed = completed;
        subListItems = new ArrayList<>();

        priority = Priority.needed;
        }

    public void AddNewSubListItem(String content)
        {
        completed = false;
        //System.out.println("Creating: " + content);
        subListItems.add(new SubListItem("",content, false, this) );
        }

    public void SetCompleted(boolean completed)
        {
        if(CheckComplete())
            {
            this.completed = completed;
            }
        else
            {
            this.completed = false;
            }
        }

    public boolean CheckComplete()
        {
        boolean returnValue = true;

        for(SubListItem i : subListItems)
            {
            //System.out.println(i.getCompleted());
            //If a single item is not complete return false
            if(!i.getCompleted())
                {
                returnValue = false;
                break;
                }
            }

        // System.out.println(returnValue);
        return (returnValue);
        }

    public boolean getCompleted()
        {
        return(completed);
        }

    public void RemoveItem(SubListItem subListItem)
        {
        subListItems.remove(subListItem);
        }

    public void VerifyChildren()
        {
        for(SubListItem i : subListItems)
            {
            i.UpdateParent(this);
            }
        }
    }
