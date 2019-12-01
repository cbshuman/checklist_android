package com.example.cs356_project.dataModel;

public class SubListItem extends Note
    {
    private boolean completed;
    private CheckListItem parentItem;

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
    }
