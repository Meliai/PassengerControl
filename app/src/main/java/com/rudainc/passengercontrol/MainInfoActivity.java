package com.rudainc.passengercontrol;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.rudainc.passengercontrol.util.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainInfoActivity extends Activity  implements Validator.ValidationListener {

    String transports[] = {"Тролейбус", "Автобус", "Трамвай"};
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.transport)
    AppCompatSpinner spTransport;
    @NotEmpty
    @BindView(R.id.etRoute)
    EditText etRoute;
    @NotEmpty
    @BindView(R.id.etBoardNumber)
    EditText etBoardNumber;
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

    @OnClick(R.id.rlDatePicker)
    void pickDate() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @OnClick(R.id.rlTimePicker)
    void pickTime() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @OnClick(R.id.go)
    void forward() {
        Log.i("MAIN","gogo");
        Intent intent = new Intent(MainInfoActivity.this,FeedbackActivity.class);
        startActivity(intent);

//        validator.validate();

    }

    private void saveData() {
        Data.setData(this, date, time, transport, etRoute.getText().toString(), etBoardNumber.getText().toString());
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_info);

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        date = mDay + "." + (mMonth+1) + "." + mYear;
        time = sdf.format(Calendar.getInstance().getTime());

//        tvDate.setText(date);
//        tvTime.setText(time);

//        initSpinner();

        validator = new Validator(this);
        validator.setValidationListener(this);


    }

    private void initSpinner() {
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, transports);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spTransport.setAdapter(arrayAdapter);

        spTransport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Spinner", "selected" + transports[position]);
                transport = transports[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("Spinner", "unselected" + transports[0]);
                transport = transports[0];
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        Intent intent = new Intent(MainInfoActivity.this,IssuesActivity.class);
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
            date = day + "." + (month+1) + "." + year;
            tvDate.setText(date);
        }
    }
}
