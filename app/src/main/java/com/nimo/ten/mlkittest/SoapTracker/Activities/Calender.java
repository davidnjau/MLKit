package com.nimo.ten.mlkittest.SoapTracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.CalenderEventsAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapEventsRecyclerAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapHealingRecyclerAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.FetchDataBase;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.DaysPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapTrackerPojo;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Calender extends AppCompatActivity {

    private FetchDataBase fetchDataBase;

    ArrayList<DaysPojo> daysPojoArrayList = new ArrayList<>();
    private Calendar calendar;
    private int monthDate;

    CompactCalendarView compactCalendarView;
    private SimpleDateFormat sdf;
    private TextView month;
    RecyclerView recyclerView6;
    RecyclerView.LayoutManager layoutManager;
    private LinearLayout myview;
    String txtCondition = "date_out";
    private DatabaseHelper databaseHelper;

    SoapEventsRecyclerAdapter soapEventsRecyclerAdapter;
    private ArrayList<SoapTrackerPojo> soapTrackerPojoArrayList;

    ArrayList<String> myEvents = new ArrayList<>();
    List<String> mySoapNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView6 = findViewById(R.id.recyclerView);
        myview = findViewById(R.id.myview);

        fetchDataBase = new FetchDataBase();

        daysPojoArrayList = fetchDataBase.getDateOuts(Calender.this);

        calendar = Calendar.getInstance();

        compactCalendarView = findViewById(R.id.compactcalendar_view);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        month = findViewById(R.id.month);

        sdf = new SimpleDateFormat("yyyy-MM-dd");

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                myEvents.clear();

                List<Event> events = compactCalendarView.getEvents(dateClicked);

                for (int i =0; i<events.size(); i++){

                    Event txtEvents = events.get(i);

                    myEvents.add(txtEvents.getData().toString());

                }
//
                InputDialog();

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                String month_name = month_date.format(firstDayOfNewMonth.getTime());

                month.setText(month_name);

            }
        });
    }

    private void InputDialog() {

        LayoutInflater li = LayoutInflater.from(Calender.this);
        View promptsView = li.inflate(R.layout.cardview_calender_events, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Calender.this);

        alertDialogBuilder.setView(promptsView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Calender.this);

        RecyclerView recyclerView = promptsView.findViewById(R.id.recyclerView6);
        LinearLayout myview1 = promptsView.findViewById(R.id.myview);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        CalenderEventsAdapter calenderEventsAdapter = new CalenderEventsAdapter(this, myEvents);
        recyclerView.setAdapter(calenderEventsAdapter);

        if (calenderEventsAdapter.getItemCount() == 0){

            myview1.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        }else {

            myview1.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        }

        final AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();


    }


    @Override
    protected void onStart() {
        super.onStart();

        InitCalender();



        startRecylerView();

    }

    private void InitCalender() {

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());

        month.setText(month_name);

        for (int i = 0; i < daysPojoArrayList.size(); i++){

            String txtDateOut = daysPojoArrayList.get(i).getDateOut();
            setCalenderEvent(txtDateOut, daysPojoArrayList.get(i).getSoapName());

            mySoapNames.add(daysPojoArrayList.get(i).getSoapName());

        }

        daysPojoArrayList.clear();
        mySoapNames.clear();

    }

    private void startRecylerView() {

        recyclerView6.setLayoutManager(layoutManager);
        recyclerView6.setHasFixedSize(true);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        soapTrackerPojoArrayList = databaseHelper.getSoapCuringDates();
        soapEventsRecyclerAdapter = new SoapEventsRecyclerAdapter(getApplicationContext(), (ArrayList<SoapTrackerPojo>) soapTrackerPojoArrayList);
        recyclerView6.setAdapter(soapEventsRecyclerAdapter);

        if (soapEventsRecyclerAdapter.getItemCount() == 0){

            myview.setVisibility(View.VISIBLE);
            recyclerView6.setVisibility(View.GONE);

        }else {

            myview.setVisibility(View.GONE);
            recyclerView6.setVisibility(View.VISIBLE);

        }

    }


    private int getMonthNumber(String txtMonth){

        switch (txtMonth){

            case "Jan":
                monthDate = 1;
                break;
            case "Feb":
                monthDate = 2;
                break;
            case "Mar":
                monthDate = 3;
                break;
            case "Apr":
                monthDate = 4;
                break;
            case "May":
                monthDate = 5;
                break;
            case "Jun":
                monthDate = 6;
                break;
            case "Jul":
                monthDate = 7;
                break;
            case "Aug":
                monthDate = 8;
                break;
            case "Sep":
                monthDate = 9;
                break;
            case "Oct":
                monthDate = 10;
                break;

            case "Nov":
                monthDate = 11;
                break;
            case "Dec":
                monthDate = 12;
                break;

        }

        return monthDate;

    }


    private void setCalenderEvent(String txtDateOut, String soapName){

        try {


            String dateOut = txtDateOut.substring(7,11) + "-" +  getMonthNumber(txtDateOut.substring(3,6)) + "-" + txtDateOut.substring(0,2);
            Date date = new Date(sdf.parse(dateOut).getTime());
            Event ev1 = new Event(Color.GREEN, date.getTime(), soapName);
            compactCalendarView.addEvent(ev1);


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.icon_calender) {



        }else if (id == R.id.icon_profile ){

            Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();

        }else if (id == R.id.icon_Settings){

            Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();

        }else if (id == R.id.icon_exit_app){

            ExitDialog();

        }

        return super.onOptionsItemSelected(item);
    }

    private void ExitDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure You want to exit the app");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
