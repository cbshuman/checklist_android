package com.example.cs356_project.dataModel;

import java.util.Comparator;

public class CheckListItemSorter implements Comparator<CheckListItem>
    {
    @Override
    public int compare(CheckListItem a,CheckListItem o)
        {
        System.out.println("--- Comparing ---");
        int returnValue = 0;
        if(o.getCompleted() &&  a.getCompleted())
            {

            //The other has a higher priority
            if(o.priority.ordinal() > a.priority.ordinal())
                {
                returnValue = 1;
                }
            //This has a higher priority
            else if(o.priority.ordinal() < a.priority.ordinal())
                {
                returnValue = -1;
                }
            //Perfectly balanced as all things should be (equal) if neither run
            }
        //The other is done, this isn't
        else if(o.getCompleted())
            {
            returnValue = -1;
            }
        //This is done the other isn't
        else
            {
            returnValue = 1;
            }

        return (returnValue);
        }
    }
