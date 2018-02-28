package com.example.caporal.tecnutriapp.utils;

/**
 * Created by caporal on 28/02/18.
 */

public class DateUtils {

    public static String getDateFormated(String date){
        String newDate = date.replaceAll("-", "");
        return newDate.substring(6,8) + "/" + newDate.substring(4,6) + "/" + newDate.substring(0,4);
    }

}
