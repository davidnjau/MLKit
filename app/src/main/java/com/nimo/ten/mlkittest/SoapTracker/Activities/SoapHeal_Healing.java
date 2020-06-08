package com.nimo.ten.mlkittest.SoapTracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapCheckerAdpater;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapHealedRecyclerAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapHealingRecyclerAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.TabAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.CreateAlarm;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapTrackerPojo;

import java.util.ArrayList;
import java.util.List;

public class SoapHeal_Healing extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    SoapHealedRecyclerAdapter soapHealedRecyclerAdapter;
    SoapHealingRecyclerAdapter soapHealingRecyclerAdapter;

    List<SoapTrackerPojo> soapTrackerPojoArrayList, soapTrackerPojoArrayList1;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soap_heal__healing);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        soapTrackerPojoArrayList = databaseHelper.getSoapTrack("Healed");
        soapTrackerPojoArrayList1 = databaseHelper.getSoapTrack("Healing");

        soapHealedRecyclerAdapter = new SoapHealedRecyclerAdapter(getApplicationContext(), (ArrayList<SoapTrackerPojo>) soapTrackerPojoArrayList);
        soapHealingRecyclerAdapter = new SoapHealingRecyclerAdapter(getApplicationContext(), (ArrayList<SoapTrackerPojo>) soapTrackerPojoArrayList1);

        auth = FirebaseAuth.getInstance();

        tabLayout.addTab(tabLayout.newTab().setText("Prepared " + soapHealingRecyclerAdapter.getItemCount()));
        tabLayout.addTab(tabLayout.newTab().setText("Cured " + soapHealedRecyclerAdapter.getItemCount()));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), this,
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), UploadSoapDetails.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        SoapCheckerAdpater soapCheckerAdpater = new SoapCheckerAdpater();
        soapCheckerAdpater.getSoapCondition(SoapHeal_Healing.this);

        CreateAlarm createAlarm = new CreateAlarm();
        createAlarm.StartAlarmWork(SoapHeal_Healing.this);

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

            startActivity(new Intent(getApplicationContext(), Calender.class));

        }else if (id == R.id.icon_profile ){

            startActivity(new Intent(getApplicationContext(), Profile.class));

        }else if (id == R.id.icon_Settings){

            Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();

        }else if (id == R.id.icon_exit_app){

            ExitDialog();

        }else if (id == R.id.icon_Sign_Out){

            auth.signOut();

            Intent intent = new Intent(this, AccountManagement.class);
            startActivity(intent);
            finish();

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
