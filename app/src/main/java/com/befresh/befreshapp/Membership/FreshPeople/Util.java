package com.befresh.befreshapp.Membership.FreshPeople;

/**
 * Created by iagomendesfucolo on 24/03/17.
 */

public class Util {


    public static int getImageMood(int i){

        return 1;
    }


    public static boolean resolveDate(int monthDate, int actualMonth){

        if (monthDate != actualMonth)
            return false;

        return true;
    }

    public static boolean setImageVisiblity(int[] date, int day, int month, int year){
        return date[0] == day && date[1] == month - 1 && date[2] == year;
    }

    public static String getMonth(int month) {
        switch (month) {
            case 1:
                return "01";
            case 2:
                return "02";
            case 3:
                return "03";
            case 4:
                return "04";
            case 5:
                return "05";
            case 6:
                return "06";
            case 7:
                return "07";
            case 8:
                return "08";
            case 9:
                return "09";
            case 10:
                return "10";
            case 11:
                return "11";
            case 12:
                return "12";
        }
        return "";
    }
}
