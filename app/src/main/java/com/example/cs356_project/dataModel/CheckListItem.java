package com.example.cs356_project.dataModel;

import android.widget.CheckBox;

public class CheckListItem extends Note
    {
    public boolean completed;

    public CheckListItem(String id,String contents, boolean completed)
        {
        super(id,contents);
        this.completed = completed;
        }
    }
