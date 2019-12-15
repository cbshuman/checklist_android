package com.example.cs356_project.Serialization;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class Serializer
    {
    public static void WriteToFile(String targetFile, String data, Context context)
        {
        System.out.println("Writting to file: " + targetFile + "\n" + data);
        try
            {
            FileOutputStream fileStream = context.openFileOutput(targetFile, Context.MODE_PRIVATE);

            fileStream.write(data.getBytes());

            fileStream.close();
            }
        catch (Exception e)
            {
            System.out.println("Error Writing to File!");
            e.printStackTrace();
            }
        }

    public static String ReadFromFile(String targetFile, Context context)
        {
        StringBuilder returnValue = new StringBuilder();

        try
            {
            FileInputStream fileStream = context.openFileInput(targetFile);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
            String nextLine = reader.readLine();

            while (nextLine != null)
                {
                returnValue.append(nextLine);
                nextLine = reader.readLine();
                }

            fileStream.close();
            }
        catch (Exception e)
            {
            System.out.println("Error Writing to File!");
            e.printStackTrace();
            }

        //System.out.println("---------> Read in: " + "\n" + returnValue.toString());

        //Return whatever was in the file
        return (returnValue.toString());
        }

    public static boolean CheckIfFileExists(String targetFile, Context context)
        {
        boolean returnValue = false;

        try
            {
            FileInputStream fileStream = context.openFileInput(targetFile);
            fileStream.close();
            returnValue = true;
            }
        catch (Exception e)
            {
            }

        return(returnValue);
        }
    }