package id.ac.uny.riset.ride.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.ac.uny.riset.ride.data.Preferences;
import id.ac.uny.riset.ride.data.model.UserModel;
import lib.almuwahhid.utils.LibUi;

public class Function {

    public static Gson gson = new Gson();

    public static void setUserPreference(Context context, String sp){
        LibUi.setSPString(context, Preferences.USER_ACCESS, sp);
    }

    public static UserModel getUserPreference(Context context){
        if(LibUi.getSPString(context, Preferences.USER_ACCESS).equals("")){
            return null;
        } else {
            return gson.fromJson(LibUi.getSPString(context, Preferences.USER_ACCESS), UserModel.class);
        }
    }

    public static void logoutUser(Context context){
        LibUi.removeSPString(context, Preferences.USER_ACCESS);
    }

    public static boolean checkUserPreference(Context context){
        if(LibUi.getSPString(context, Preferences.USER_ACCESS).equals("")){
            return false;
        } else {
            return true;
        }
    }

    public static String parseTimestampToDate(String data){
        try {
            String begin = data.split(" ")[0];
            return begin.split("-")[2]+" "+ LibUi.monthName(Integer.valueOf(begin.split("-")[1])-1)+ " "+begin.split("-")[0];
        } catch (Exception e){
            return "";
        }
    }

    public static String parseTimestampToSimpleMonthYear(String data){
        try {
            String begin = data.split(" ")[0];
            return simpleMonthName(Integer.valueOf(begin.split("-")[1])-1)+ " "+begin.split("-")[0];
        } catch (Exception e){
            return "";
        }
    }
    public static String parseDateStringToDate(String data){
        try {
            return data.split("-")[2]+" "+simpleMonthName(Integer.valueOf(data.split("-")[1])-1)+ " "+data.split("-")[0];
        } catch (Exception e){
            return "";
        }
    }
    public static String parseTimestampToSimpleFullDate(String data){
        try {
            String begin = data.split(" ")[0];
            return begin.split("-")[2]+" "+ simpleMonthName(Integer.valueOf(begin.split("-")[1])-1)+ " "+begin.split("-")[0].substring(2, 4);
        } catch (Exception e){
            return "";
        }
    }
    public static String parseTimestampToSimpleDate(String data){
        try {
            String begin = data.split(" ")[0];
            return begin.split("-")[2];
        } catch (Exception e){
            return "";
        }
    }

    public static String simpleMonthName(int month) {
        String m = "";
        switch (month) {
            case 0:
                m = "Jan";
                break;
            case 1:
                m = "Feb";
                break;
            case 2:
                m = "Mar";
                break;
            case 3:
                m = "Apr";
                break;
            case 4:
                m = "Mei";
                break;
            case 5:
                m = "Jun";
                break;
            case 6:
                m = "Jul";
                break;
            case 7:
                m = "Aug";
                break;
            case 8:
                m = "Sept";
                break;
            case 9:
                m = "Okt";
                break;
            case 10:
                m = "Nov";
                break;
            case 11:
                m = "Des";
                break;
        }
        return m;
    }

    public static String isToday(String data) {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            String str1 = data.split(" ")[0].replaceAll("-", "/");
            Date date1 = new LocalDate(formatter.parse(str1)).toDate();
            Date date2 = new LocalDate(new Date()).toDate();

            Log.d("isToday", "isToday: "+date1+" now "+date2);
            if (date1.compareTo(date2) < 0) {
                return "yesterday";
            } else if (date1.compareTo(date2) == 0) {
                return "today";
            } else {
                return "tomorrow";
            }

        } catch (ParseException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
