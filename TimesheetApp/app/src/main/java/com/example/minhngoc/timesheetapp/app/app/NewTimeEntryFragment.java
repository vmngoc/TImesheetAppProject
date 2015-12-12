package com.example.minhngoc.timesheetapp.app.app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.minhngoc.timesheetapp.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by minhngoc on 12/7/15.
 */
public class NewTimeEntryFragment extends DialogFragment {
    Button buttonAddNew;
    TextView viewStartDate;
    TextView viewStartTime;
    TextView viewStopDate;
    TextView viewStopTime;
    View viewPicker;

    public static NewTimeEntryFragment newInstance(Bundle bundle) {
        NewTimeEntryFragment myFragment = new NewTimeEntryFragment();
        myFragment.setArguments(bundle);
        return myFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_time_picker, container,
                false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        viewStartDate = (TextView) view.findViewById(R.id.viewStartDate);
        viewStartTime = (TextView) view.findViewById(R.id.viewStartTime);
        viewStopDate = (TextView) view.findViewById(R.id.viewStopDate);
        viewStopTime = (TextView) view.findViewById(R.id.viewStopTime);
        buttonAddNew = (Button) view.findViewById(R.id.buttonAddNew);
        viewPicker = (View) view.findViewById(R.id.viewPicker);

        Date now = new Date();
        viewStartDate.setText(DateFormat.getDateInstance().format(now));
        viewStopDate.setText(DateFormat.getDateInstance().format(now));
        viewStartTime.setText(DateFormat.getTimeInstance().format(now));
        viewStopTime.setText(DateFormat.getTimeInstance().format(now));

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(),
                        "Please enter date and time for a new time entry.",
                        Toast.LENGTH_LONG).show();
            }
        });

        viewStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showDatePickerDialog(viewPicker);
            }
        });

        viewStopDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showDatePickerDialog(viewPicker);
            }
        });

        viewStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showTimePickerDialog(viewPicker);
            }
        });

        viewStopTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showTimePickerDialog(viewPicker);
            }
        });

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getChildFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getChildFragmentManager(), "datePicker");
    }

    /**
     * Time picker fragment that allows user manually enters a new time entry
     */
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    android.text.format.DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
        }
    }

    public static class DatePickerFragment extends DialogFragment
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
        }
    }
}