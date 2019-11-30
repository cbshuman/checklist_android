package com.example.cs356_project.dataModel;

public abstract class Note
    {
    protected String id;
    protected String contents;

    public Note(String id, String contents)
        {
        this.id = id;
        this.contents = contents;
        }

    public String GetContents()
        {
        return(contents);
        }
    }
