package com.rudainc.passengercontrol;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.rudainc.passengercontrol.transport_layout.Transport;
import com.rudainc.passengercontrol.transport_layout.TransportLinearLayout;
import com.rudainc.passengercontrol.util.Data;

import io.fabric.sdk.android.Fabric;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainInfoActivity extends BaseActivity implements Validator.ValidationListener, TransportLinearLayout.OnTransportChangeListener {

    String transports[];

    @BindView(R.id.tvDate)
    TextView tvDate;

    @BindView(R.id.tvTime)
    TextView tvTime;

    @NotEmpty
    @BindView(R.id.etRoute)
    EditText etRoute;

    @NotEmpty
    @BindView(R.id.etBoardNumber)
    EditText etBoardNumber;

    @BindView(R.id.ad_container)
    AdView mAdView;

    @BindView(R.id.ll_trasport_type)
    TransportLinearLayout mTransportLinearLayout;

    private int mYear;
    private int mMonth;
    private int mDay;
    private String date;
    private String time;
    private String transport;
    private ArrayAdapter<String> arrayAdapter;
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    date = mDay + "." + mMonth + 1 + "." + mYear;

                    tvDate.setText(date);
                }
            };
    private Validator validator;

    @OnClick(R.id.getBoardNumber)
    void showDialog(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(getString(R.string.how_get_number))
                .setMessage(getString(R.string.how_get_number_text))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

//          .setIcon(android.R.drawable.ic_dialog_alert)
    }

    @OnClick(R.id.llDatePicker)
    void pickDate() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @OnClick(R.id.llTimePicker)
    void pickTime() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @OnClick(R.id.go)
    void forward() {
        validator.validate();
    }

    private void saveData() {
        Data.setData(this, date, time, transport, etRoute.getText().toString(), etBoardNumber.getText().toString());
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main_info);
        ButterKnife.bind(this);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mTransportLinearLayout.setOnTransportChangeListener(this);
        transport  = getString(R.string.trolleybus);

        transports = getResources().getStringArray(R.array.transport);
        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        date = mDay + "." + (mMonth + 1) + "." + mYear;
        time = sdf.format(Calendar.getInstance().getTime());

        tvDate.setText(date);
        tvTime.setText(time);


        validator = new Validator(this);
        validator.setValidationListener(this);

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

    @Override
    public void onValidationSucceeded() {
        Intent intent = new Intent(MainInfoActivity.this, IssuesActivity.class);
        startActivity(intent);
        saveData();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = getResources().getString(R.string.error_empty_field);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onChange(Transport item) {
        Log.i("Transport",item.getTitle());
        transport = item.getTitle();
    }


    @SuppressLint("ValidFragment")
    public class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            time = hourOfDay + ":" + minute;
            tvTime.setText(time);

        }
    }

    @SuppressLint("ValidFragment")
    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            date = day + "." + (month + 1) + "." + year;
            tvDate.setText(date);
        }
    }
}
