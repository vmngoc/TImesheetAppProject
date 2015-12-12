package com.example.minhngoc.timesheetapp.app.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.minhngoc.timesheetapp.R;
import com.example.minhngoc.timesheetapp.app.app.AppConfig;
import com.example.minhngoc.timesheetapp.app.app.AppController;
import com.example.minhngoc.timesheetapp.app.app.NewTimeEntryFragment;
import com.example.minhngoc.timesheetapp.app.helper.ListViewAdapter;
import com.example.minhngoc.timesheetapp.app.helper.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.minhngoc.timesheetapp.app.helper.Constants.FIRST_COLUMN;
import static com.example.minhngoc.timesheetapp.app.helper.Constants.SECOND_COLUMN;
import static com.example.minhngoc.timesheetapp.app.helper.Constants.THIRD_COLUMN;

public class TimeRecordActivity extends AppCompatActivity {
    private static final String TAG = TimeRecordActivity.class.getSimpleName();
    private long startTime;
    private long stopTime;
    private int secondsElapsed;
    private int totalTime;
    private Boolean timerIsRunning;
    private LinkedList<HashMap<String, String>> entryList = new LinkedList<HashMap<String,String>>();

    private View viewNewEntry;
    private TextView viewClockInTime;
    private TextView viewClockOutTime;
    private TextView viewTimeElapsed;
    private Button btnClockInOut;
    private ListView listView;
    private ListViewAdapter adapter;
    private TextView viewTotalTime;

    private SQLiteHandler db;
    private ProgressDialog pDialog;

    Timer timer;
    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_record);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        viewNewEntry = (View) findViewById(R.id.viewNewEntry);
        viewClockInTime = (TextView) findViewById(R.id.clockInTime);
        viewClockOutTime = (TextView) findViewById(R.id.clockOutTime);
        viewTimeElapsed = (TextView) findViewById(R.id.timeElapsed);
        btnClockInOut = (Button) findViewById(R.id.btnClockInOut);
        listView = (ListView) findViewById(R.id.listTimeEntries);
        viewTotalTime = (TextView) findViewById(R.id.totalTime);

        adapter = new ListViewAdapter(this, entryList);
        listView.setAdapter(adapter);

        timerIsRunning = false;

        // Clock in & out button click event
        btnClockInOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // when timer is timing, this serves as 'clock out' button
                if (timerIsRunning) {
                    stopTime = System.currentTimeMillis();;
                    viewClockOutTime.setText(formatTime(stopTime));

                    // reformat button text and background
                    btnClockInOut.setBackground(getDrawable(R.drawable.button_clock_in));
                    btnClockInOut.setText(R.string.btn_clock_in);

                    // terminate timer
                    timer.cancel();
                    timerIsRunning = false;

                    // add new time entry to listView
                    addTimeEntryToList();

                    // display total work time
                    totalTime += secondsElapsed;
                    viewTotalTime.setText(formatTimeElapsed(totalTime));

                    // add time entry to database
                    recordTimeEntry(1, formatTime(startTime), formatTime(stopTime));

                } else { // when timer isn't running, this serves as 'clock in' button
                    startTime = System.currentTimeMillis();
                    viewClockInTime.setText(formatTime(startTime));

                    // reformat button text and color
                    btnClockInOut.setBackground(getDrawable(R.drawable.button_clock_out));
                    btnClockInOut.setText(R.string.btn_clock_out);

                    // start timer
                    secondsElapsed = 0;
                    rescheduleTimer();
                    timerIsRunning = true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_record, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_add_new_entry:
                // User chose the "Add new entry" action, open a fragment to let user
                // manually create a new time entry
                showTimePickerDialog(viewNewEntry);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Function to store new time entry with the user id in MySQL database will post params(user_id,
     * start_time, stop_time) to time record url
     * */
    private void recordTimeEntry(final int userId, final String startTime,
                              final String stopTime) {
        // Tag used to cancel the request
        String tag_string_req = "req_time_record";

        pDialog.setMessage("Recording ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_TIME_RECORD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Time Record Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("user_id");

                        JSONObject entry = jObj.getJSONObject("time_entry");
                        String startTime = entry.getString("start_time");
                        String stopTime = entry.getString("stop_time");

                        // Inserting row in users table
                        db.addTimeEntry(uid, startTime, stopTime);

                        Toast.makeText(getApplicationContext(), "Successfully recorded a new time entry!", Toast.LENGTH_LONG).show();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Time Record Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", Integer.toString(userId));
                params.put("start_time", startTime);
                params.put("stop_time", stopTime);

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    /**
     * Reset the timer that shows time elapsed
     */
    public void rescheduleTimer(){
        timer = new Timer();
        timerTask = new myTimerTask();
        timer.schedule(timerTask, 0, 1000);
    }

    /**
     * TimerTask that keeps track of time elapsed and updates it in the time-elapsed view
     */
    private class myTimerTask extends TimerTask{
        @Override
        public void run() {
            secondsElapsed++;
            updateTimeElapsed.sendEmptyMessage(0);
        }
    }

    private Handler updateTimeElapsed = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            viewTimeElapsed.setText(formatTimeElapsed(secondsElapsed));
        }
    };

    /**
     * Add the new time entry to the list view
     */
    public void addTimeEntryToList() {
        HashMap<String,String> temp=new HashMap<String, String>();
        temp.put(FIRST_COLUMN, formatTime(startTime));
        temp.put(SECOND_COLUMN, formatTime(stopTime));
        temp.put(THIRD_COLUMN, formatTimeElapsed(secondsElapsed));
        // temp.put(FOURTH_COLUMN, earning);
        entryList.addFirst(temp);
        runOnUiThread(new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     *
     * @param time
     * @return String representation in readable format of the time
     */
    private String formatTime(long time) {
        return DateFormat.getDateTimeInstance().format(new Date(time));
    }

    /**
     *
     * @param secondsElapsed
     * @return time elapsed in hours, minutes, seconds format
     */
    private String formatTimeElapsed(int secondsElapsed) {
        int seconds = secondsElapsed % 60;
        int minutes = (secondsElapsed / 60) % 60;
        int hours = (secondsElapsed / 3600);
        return String.format("%02dh %02dm %02ds", hours, minutes, seconds);
    }

    /**
     * Show time picker to manually add new time entry
     * @param v
     */
    public void showTimePickerDialog(View v) {
//        DialogFragment newFragment = new NewTimeEntryFragment();
//        newFragment.show(getSupportFragmentManager(), "timePicker");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DialogFragment fragment = new NewTimeEntryFragment();
        fragment.show(getSupportFragmentManager(), "timePicker");
    }

    /**
//     * Time picker fragment that allows user manually enters a new time entry
//     */
//    public static class NewTimeEntryFragment extends DialogFragment
//            implements TimePickerDialog.OnTimeSetListener {
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // Use the current time as the default values for the picker
//            final Calendar c = Calendar.getInstance();
//            int hour = c.get(Calendar.HOUR_OF_DAY);
//            int minute = c.get(Calendar.MINUTE);
//
//            // Create a new instance of TimePickerDialog and return it
//            return new TimePickerDialog(getActivity(), this, hour, minute,
//                    android.text.format.DateFormat.is24HourFormat(getActivity()));
//        }
//
//        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//            // Do something with the time chosen by the user
//        }
//    }
}
