package com.example.cs356_project.dataModel;

import java.util.Calendar;

public class Date
    {
    public int day;
    public int month;
    public int year;

    public Date(int year, int month, int day)
        {
        this.year = year;
        this.month = month;
        this.day = day;
        }

    public Date()
        {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        }

    public Date(long date)
        {
        //Get the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);

        //Set the info
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        }

    @Override
    public String toString()
        {
        StringBuilder dateBulder = new StringBuilder();

        switch (month)
            {
            case 0:
                dateBulder.append("Jan");
                break;
            case 1:
                dateBulder.append("Feb");
                break;
            case 2:
                dateBulder.append("Mar");
                break;
            case 3:
                dateBulder.append("Apr");
                break;
            case 4:
                dateBulder.append("May");
                break;
            case 5:
                dateBulder.append("Jun");
                break;
            case 6:
                dateBulder.append("Jul");
                break;
            case 7:
                dateBulder.append("Aug");
                break;
            case 8:
                dateBulder.append("Sep");
                break;
            case 9:
                dateBulder.append("Oct");
                break;
            case 10:
                dateBulder.append("Nov");
                break;
            case 11:
                dateBulder.append("Dec");
                break;
            }

        dateBulder.append(" ");
        dateBulder.append(day);
        dateBulder.append(", ");
        dateBulder.append(year);

        return(dateBulder.toString());
        }

    public long GetAsLong()
        {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        return(calendar.getTimeInMillis());
        }
    }
