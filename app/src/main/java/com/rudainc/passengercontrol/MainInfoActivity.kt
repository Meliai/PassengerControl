package com.rudainc.passengercontrol

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.format.DateFormat
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast

import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.crashlytics.android.answers.CustomEvent
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.rudainc.passengercontrol.transport_layout.Transport
import com.rudainc.passengercontrol.transport_layout.TransportLinearLayout
import com.rudainc.passengercontrol.util.Data

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import io.fabric.sdk.android.Fabric

class MainInfoActivity : BaseActivity(), Validator.ValidationListener, TransportLinearLayout.OnTransportChangeListener {

    private lateinit var transports: Array<String>

    @BindView(R.id.tvDate)
    internal var tvDate: TextView? = null

    @BindView(R.id.tvTime)
    internal var tvTime: TextView? = null

    @NotEmpty
    @BindView(R.id.etRoute)
    internal var etRoute: EditText? = null

    @NotEmpty
    @BindView(R.id.etBoardNumber)
    internal var etBoardNumber: EditText? = null

    @BindView(R.id.ad_container)
    internal var mAdView: AdView? = null

    @BindView(R.id.ll_trasport_type)
    internal var mTransportLinearLayout: TransportLinearLayout? = null

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var date: String? = null
    private var time: String? = null
    private var transport: String? = null

    private val mDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        date = mDay.toString() + "." + mMonth + 1 + "." + mYear

        tvDate!!.text = date
    }
    private var validator: Validator? = null

    @OnClick(R.id.getBoardNumber)
    internal fun showDialog() {
        val builder: AlertDialog.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
        } else {
            builder = AlertDialog.Builder(this)
        }
        builder.setTitle(getString(R.string.how_get_number))
                .setMessage(getString(R.string.how_get_number_text))
                .setPositiveButton(android.R.string.yes) { dialog, which -> dialog.dismiss() }
                .show()

        //          .setIcon(android.R.drawable.ic_dialog_alert)
    }

    @OnClick(R.id.llDatePicker)
    internal fun pickDate() {
        val newFragment = DatePickerFragment()
        newFragment.show(fragmentManager, "datePicker")
    }

    @OnClick(R.id.llTimePicker)
    internal fun pickTime() {
        val newFragment = TimePickerFragment()
        newFragment.show(fragmentManager, "timePicker")
    }

    @OnClick(R.id.go)
    internal fun forward() {
        validator!!.validate()
    }

    private fun saveData() {
        Data.setData(this, date, time, transport, etRoute?.text.toString(), etBoardNumber?.text.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_info)
        ButterKnife.bind(this)

        val adRequest = AdRequest.Builder().build()
        mAdView?.loadAd(adRequest)

        mTransportLinearLayout?.setOnTransportChangeListener(this)
        transport = getString(R.string.trolleybus)

        transports = resources.getStringArray(R.array.transport)
        // get the current date
        val c = Calendar.getInstance()
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)

        val sdf = SimpleDateFormat("HH:mm")

        date = mDay.toString() + "." + (mMonth + 1) + "." + mYear
        time = sdf.format(Calendar.getInstance().time)

        tvDate?.text = date
        tvTime?.text = time


        validator = Validator(this)
        validator?.setValidationListener(this)

        //        setupWindowAnimations();

    }

    //    private void setupWindowAnimations() {
    //
    //        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
    //            Slide slide = new Slide();
    //            slide.setDuration(1000);
    //            getWindow().setExitTransition(slide);
    //        }
    //
    //    }

    override fun onValidationSucceeded() {
        val intent = Intent(this@MainInfoActivity, IssuesActivity::class.java)
        startActivity(intent)
        saveData()
        Answers.getInstance().logCustom(CustomEvent(getString(R.string.event_transport))
                .putCustomAttribute(getString(R.string.event_transport_type), transport))
        Answers.getInstance().logCustom(CustomEvent(getString(R.string.event_go_to_issues)))
    }

    override fun onValidationFailed(errors: List<ValidationError>) {
        for (error in errors) {
            val view = error.view
            val message = resources.getString(R.string.error_empty_field)

            // Display error messages ;)
            if (view is EditText) {
                view.error = message
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onChange(item: Transport) {
        transport = item.title
    }


    @SuppressLint("ValidFragment")
    inner class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

        override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
            // Use the current time as the default values for the picker
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            // Create a new instance of TimePickerDialog and return it
            return TimePickerDialog(activity, this, hour, minute,
                    DateFormat.is24HourFormat(activity))
        }

        override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
            // Do something with the time chosen by the user
            time = "$hourOfDay:$minute"
            tvTime?.text = time

        }
    }

    @SuppressLint("ValidFragment")
    inner class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

        override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
            // Use the current date as the default date in the picker
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dialog = DatePickerDialog(activity, this, year, month, day)
            dialog.datePicker.maxDate = Date().time
            // Create a new instance of DatePickerDialog and return it
            return dialog
        }

        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
            // Do something with the date chosen by the user
            date = day.toString() + "." + (month + 1) + "." + year
            tvDate?.text = date
        }
    }
}
