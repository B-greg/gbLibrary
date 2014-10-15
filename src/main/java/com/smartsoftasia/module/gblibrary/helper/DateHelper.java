package com.smartsoftasia.module.gblibrary.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by gregoire on 10/15/14.
 */
public class DateHelper {


    public static Date stringToDate(String strDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        Date date= null;
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String stringToHours(String strDate){

        Date dt = stringToDate(strDate);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm aa");
        String time = dateFormatter.format(dt);
        return time;
    }

    public static String stringToDay(String strDate){

        Date dt = stringToDate(strDate);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMMM d");
        String time = dateFormatter.format(dt);
        return time;
    }

    public static GregorianCalendar stringToGregorianCalendar(String strDate){
        Date date;
        GregorianCalendar gregorianCalendar = null;
        try {
            date = stringToDate(strDate);
            gregorianCalendar =(GregorianCalendar) GregorianCalendar.getInstance();
            // by below we are setting the time of date into gregorianCalendar
            gregorianCalendar.setTime(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return gregorianCalendar;
    }
}
