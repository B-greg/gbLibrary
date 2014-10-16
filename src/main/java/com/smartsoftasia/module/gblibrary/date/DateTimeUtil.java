/**
 * @file DateUtil.java, DateUtil
 * @author gregoire
 * @date Apr 9, 2014
 * @brief
 *
 * @detail
 *
 * @project gblibrary
 *
 */
package com.smartsoftasia.module.gblibrary.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.util.Log;

/**
 * @author gregoire
 *
 */
public class DateTimeUtil {

	private static final String TAG = "DateUtil";
	private static SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 


	public static String dateTimeToString(Date date) {
		if(date!= null){
		    return(formatter.format(date));
		}
		else
			return "";
	}

	public static Date stringToDate(String date) throws ParseException {
	    formatter.setTimeZone(TimeZone.getTimeZone("gmt"));
		return(formatter.parse(date));
	}

    public static String getDateByFormatedString(String date) {
        Date mDate = null;
        String dateFormated = "";
        Calendar cal=Calendar.getInstance();
        if(date != null && !date.isEmpty()){
            try {
                mDate = DateTimeUtil.stringToDate(date);
            } catch (ParseException e) {
                Log.e(TAG, "Unparsable date");
            }
        }
        if(mDate!= null){
            cal.setTime(mDate);
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            SimpleDateFormat month_date = new SimpleDateFormat("MMMMMMMMM");
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            dateFormated += dayFormat.format(cal.getTime());
            dateFormated += ", ";
            dateFormated += month_date.format(cal.getTime());
            dateFormated += " " + String.valueOf(dayOfMonth);
        }else{

        }
        return dateFormated;
    }

    public static int getHashByDay(String date){
        Date mDate = null;
        int dateInt = 0;
        Calendar cal=Calendar.getInstance();
        if(date != null && !date.isEmpty()){
            try {
                mDate = DateTimeUtil.stringToDate(date);
            } catch (ParseException e) {
                Log.e(TAG, "Unparsable date");
            }
        }
        if(mDate!= null){
            cal.setTime(mDate);
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            SimpleDateFormat month_date = new SimpleDateFormat("MMMMMMMMM");
            int dayOfMonth = cal.get(Calendar.DAY_OF_YEAR);
            int thisYear = cal.get(Calendar.YEAR);
            dateInt = thisYear*1000;
            dateInt += dayOfMonth;

        }
        return dateInt;
    }
}
