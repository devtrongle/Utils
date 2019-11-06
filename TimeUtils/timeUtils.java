
import android.annotation.SuppressLint;
import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;


/*
 *  Date created: 11/06/2019
 *  Last updated: 11/06/2019
 *  Name project: 
 *  Description:
 *  Auth: batrong2709@gmail.com
 */

public class timeUtils {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    /**
     * function convert millisecond to string time ago
     * @param time millisecond
     * @return String time ago. ex: 3 a minutes ago
     */
    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = Calendar.getInstance().getTimeInMillis();

        if (time > now || time <= 0) {
            return "";
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    /**
     * function convert millisecond to string time
     * @param time millisecond
     * @return String time ago. ex: 3 a minutes
     */
    public static String getTimeNoAgo(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = Calendar.getInstance().getTimeInMillis();

        if (time > now || time <= 0) {
            return "";
        }


        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days";
        }
    }


    /**
     * this is method convert date time to date
     * @param time milliseconds
     * @return milliseconds type date (dd/MM/yyyy)
     */
    public static long convertDateTimeToDate(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        try {
            calendar.setTime(Objects.requireNonNull(dd_MM_yyyy.parse(dd_MM_yyyy.format(calendar.getTime()))));
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * this is method get current week
     * @param time milliseconds
     * @return week in time
     */
    public static int getTheCurrentWeek(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(convertDateTimeToDate(time));
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * this is method get list time zone
     * @return list string time zone
     */
    @SuppressLint("DefaultLocale")
    public static ArrayList<String> getListTimeZone(){
        ArrayList<String> listTimeZone = new ArrayList<>();

        String[] ids = TimeZone.getAvailableIDs();
        for (String id : ids) {
            TimeZone d = TimeZone.getTimeZone(id);

            if (!id.matches(".*/.*")) {
                continue;
            }

            String region = id.replaceAll(".*/", "").replaceAll("_", " ");
            int hours = Math.abs(d.getRawOffset()) / 3600000;
            int minutes = Math.abs(d.getRawOffset() / 60000) % 60;
            String sign = d.getRawOffset() >= 0 ? "+" : "-";

            listTimeZone.add(String.format("(UTC %s %02d:%02d) %s", sign, hours, minutes, region));

        }

        return listTimeZone;
    }

    /**
     * this is method get time day no ago
     * @param time milliseconds
     * @return string time ex: Today, Yesterday
     */
    public static String getTimeDayNoAgo(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }
        long now = Calendar.getInstance().getTimeInMillis();
        if (time > now || time <= 0) {
            return "";
        }
        final long diff = now - time;
        if (diff < 24 * HOUR_MILLIS) {
            return "Today";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "Yesterday";
        } else {
            return diff / DAY_MILLIS + " days";
        }
    }



    /**
     * this is method convert Millisecond To String Format
     * @param time milliseconds time
     * @param dateFormat date formatter
     * @return Date strings formatted according to SimpleDateFormat
     */
    public static String convertMillisecondToStringFormat(long time, SimpleDateFormat dateFormat){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return dateFormat.format(calendar.getTime());
    }

}
