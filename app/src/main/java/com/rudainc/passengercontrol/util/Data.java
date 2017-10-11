package com.rudainc.passengercontrol.util;

import android.content.Context;
import android.preference.PreferenceManager;

import com.rudainc.passengercontrol.model.FeedbackInfo;

public class Data {


    public static void setData(Context context, String date, String time, String transport, String route, String board_number){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.DATE, date).apply();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.TIME, time).apply();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.TRANSPORT, transport).apply();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.ROUTE, route).apply();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.BOARD_NUMBER, board_number).apply();
    }


    public static void clearData(Context context){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.DATE, "").apply();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.TIME, "").apply();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.TRANSPORT, "").apply();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.ROUTE, "").apply();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.BOARD_NUMBER, "").apply();

    }

    public static FeedbackInfo getFeedbackInfo(Context context){
        FeedbackInfo feedbackInfo = new FeedbackInfo();

        feedbackInfo.date = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.DATE, "");

        feedbackInfo.time = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.TIME,"");

        feedbackInfo.transport = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.TRANSPORT,"");

        feedbackInfo.route = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.ROUTE,"");

        feedbackInfo.board_number = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.BOARD_NUMBER,"");


        return feedbackInfo;
    }



    public interface Constants {

        String DATE = "date";
        String TIME = "time";
        String TRANSPORT = "transport";
        String ROUTE = "route";
        String BOARD_NUMBER = "board_number";


    }
}
