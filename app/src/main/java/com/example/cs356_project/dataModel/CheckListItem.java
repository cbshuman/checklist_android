package com.example.cs356_project.dataModel;

import android.widget.CheckBox;

public class CheckListItem extends Note
    {
    public enum Priority
        {
        trivial,
        needed,
        important,
        veryImportant,
        essential
        }

    public boolean completed;
    public boolean reminder;


    public CheckListItem(String id,String contents, boolean completed)
        {
        super(id,contents);
        this.completed = completed;
        }
    }
