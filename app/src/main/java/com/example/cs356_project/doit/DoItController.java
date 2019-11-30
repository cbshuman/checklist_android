package com.example.cs356_project.doit;

import com.example.cs356_project.FUI.IFUI;

import java.util.Map;

public class DoItController implements IFUI
    {
    private DoItFragment fragment;

    public DoItController(DoItFragment fragment)
        {
        this.fragment = fragment;
        }

    @Override
    public void Say(String message)
        {
        //TODO : 1.Start fadein animation
        //TODO : 2.Show <message>
        //TODO : 3.Start fadeout animation
        }

    @Override
    public void DisplayChoices(String message, Map<String, String> choices)
        {

        }
    }
