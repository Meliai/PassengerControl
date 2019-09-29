package com.rudainc.passengercontrol.util

import android.content.Context
import android.preference.PreferenceManager

import com.rudainc.passengercontrol.model.FeedbackInfo

object Data {


    fun setData(context: Context, date: String?, time: String?, transport: String?, route: String, board_number: String) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.DATE, date).apply()
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.TIME, time).apply()
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.TRANSPORT, transport).apply()
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.ROUTE, route).apply()
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.BOARD_NUMBER, board_number).apply()
    }


    fun clearData(context: Context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.DATE, "").apply()
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.TIME, "").apply()
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.TRANSPORT, "").apply()
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.ROUTE, "").apply()
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.BOARD_NUMBER, "").apply()

    }

    fun getFeedbackInfo(context: Context): FeedbackInfo {
        val feedbackInfo = FeedbackInfo()

        feedbackInfo.date = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.DATE, "")

        feedbackInfo.time = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.TIME, "")

        feedbackInfo.transport = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.TRANSPORT, "")

        feedbackInfo.route = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.ROUTE, "")

        feedbackInfo.boardNumber = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.BOARD_NUMBER, "")


        return feedbackInfo
    }


    interface Constants {
        companion object {

            val DATE = "date"
            val TIME = "time"
            val TRANSPORT = "transport"
            val ROUTE = "route"
            val BOARD_NUMBER = "board_number"
        }


    }
}
