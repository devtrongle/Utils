

/*
 *  Date created: 11/06/2019
 *  Last updated: 11/06/2019
 *  Name project: 
 *  Description:
 *  Auth: batrong2709@gmail.com
 */

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class patternFormatDate {

    //format full time ex: web, Oct 23, 2019 - 10:30AM
    @SuppressLint("ConstantLocale")
    public static final SimpleDateFormat EEE_MMM_d_yyyy_hh_mm_a = new SimpleDateFormat("EEE, MMM d, yyyy - hh:mma", Locale.getDefault());

    //format time ex: Mon, Oct 28, 2019
    @SuppressLint("ConstantLocale")
    public static final SimpleDateFormat EEE_MMM_d_yyyy = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());

    //format time ex: 10:30AM
    @SuppressLint("ConstantLocale")
    public static final SimpleDateFormat hh_mm_a = new SimpleDateFormat("hh:mma", Locale.getDefault());

    //format time ex: 27/09/2019
    @SuppressLint("ConstantLocale")
    public static final SimpleDateFormat dd_MM_yyyy = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


    //format date ex: Mon, Sep 27
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat EEE_MMM_d = new SimpleDateFormat("EEE, MMM d");

    //format date ex: Sunday, October 27
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat EEEE_MMMM_dd = new SimpleDateFormat("EEEE, MMMM dd");

    //format date ex: Fri, October 25
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat EEE_MMMM_dd = new SimpleDateFormat("EEE, MMMM dd");

    //format date ex: Oct 25, 2019
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat MMM_dd_yyyy = new SimpleDateFormat("MMM dd, yyyy");

    //format date ex: Mon, Sep 27
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat EE_MMM_dd = new SimpleDateFormat("EE, MMM dd");

    //format date ex: 27/09
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat dd_MM = new SimpleDateFormat("dd/MM");

    //format date ex: October
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat MMMM = new SimpleDateFormat("MMMM");

    //format date ex: Oct
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat MMM = new SimpleDateFormat("MMM");

    //format date ex: 27
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat dd = new SimpleDateFormat("dd");
    
        //format date ex: Oct 27, 2020 - 08:20 AM
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat MMM_dd_yyyy_hh_mm_a = new SimpleDateFormat("MMM dd, yyyy - hh:mm a");

    //format date ex: 2020-03-23T09:28:35.56
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat yyyy_MM_dd_T_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

}
