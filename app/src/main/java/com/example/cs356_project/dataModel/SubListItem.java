package com.example.cs356_project.dataModel;

import java.io.Serializable;

public class SubListItem extends Note implements Serializable
    {
    private boolean completed;
    private transient CheckListItem parentItem;

    public SubListItem(String id, String contents, boolean completed,CheckListItem parentItem)
        {
        super(id, contents);
        this.completed = completed;
        this.parentItem = parentItem;
        }

    public void SetCompleted(boolean value)
        {
        completed = value;
        //parentItem.CheckComplete();
        }

    public boolean getCompleted()
        {
        return(completed);
        }

    public void RemoveFromParent()
        {
        parentItem.RemoveItem(this);
        }

    public void UpdateParent(CheckListItem parentItem)
        {
        this.parentItem = parentItem;
        }
    }
