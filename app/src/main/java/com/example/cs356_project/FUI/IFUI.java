package com.example.cs356_project.FUI;

import java.util.Map;

//Friendly user interface
public interface IFUI
    {
    void Say(String message);
    void DisplayChoices(String message, Map<String,String> choices);
    }
