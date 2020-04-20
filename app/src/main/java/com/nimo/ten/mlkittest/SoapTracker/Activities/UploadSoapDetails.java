package com.nimo.ten.mlkittest.SoapTracker.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView
        ;
import android.widget.Toast;


import com.github.dhaval2404.imagepicker.ImagePicker;
import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.CreateAlarm;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.OfflineNotification;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.ShowCustomToast;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UploadSoapDetails extends AppCompatActivity {

    private ImageView imageView1;
    private EditText etSoapName;
    private Bitmap bitmap;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    String txtFilePath;
    private SimpleDateFormat df;
    private String StartPickedDate;
    private int datePickedTime;
    private Calendar cc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_soap_details);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView1 = findViewById(R.id.imageView1);

        preferences = getApplicationContext().getSharedPreferences("Soap", MODE_PRIVATE);
        editor = preferences.edit();

        etSoapName = findViewById(R.id.etSoapName);
        cc = Calendar.getInstance();
        df = new SimpleDateFormat("dd-MMM-yyyy");

        findViewById(R.id.imgBtn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.Companion.with(UploadSoapDetails.this)
                        .crop()
                        .compress(720)
                        .maxResultSize(720, 720)
                        .start();

            }
        });

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtName = etSoapName.getText().toString();

                Bitmap bitmap1 = getImage();
//                Date c = Calendar.getInstance().getTime();
//                String StartDate = df.format(c);

                if (!TextUtils.isEmpty(txtName) && bitmap1 != null){

                    CalenderDialog(txtName);

                }else {

                    if (TextUtils.isEmpty(txtName))etSoapName.setError("Enter a valid soap name..");
                    if (bitmap1 == null) new ShowCustomToast(UploadSoapDetails.this, "Please select an image before proceeding..");

                }

            }
        });

    }

    private void CalenderDialog(final String txtName) {

        LayoutInflater li = LayoutInflater.from(UploadSoapDetails.this);
        View promptsView = li.inflate(R.layout.calender_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UploadSoapDetails.this);
        alertDialogBuilder.setView(promptsView);

        final MaterialCalendarView calendarView = promptsView.findViewById(R.id.calendarView);

        calendarView.state().edit()
                .setMaximumDate(CalendarDay.today())
                .commit();

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
                            }
                        });


        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String startSelectedDate = df.format(calendarView.getSelectedDate().getDate());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                getImage().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageInByte = baos.toByteArray();

                String finalDate = addDay(startSelectedDate, 28);

                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                long id = databaseHelper.AddSoapTracker(txtName, startSelectedDate, finalDate, imageInByte, txtFilePath);

                editor.putString("soap_id", String.valueOf(id));
                editor.apply();

                startActivity(new Intent(getApplicationContext(), SingleSoapDetails.class));

//                    StartOfflineWork(id);

                CreateAlarm createAlarm = new CreateAlarm();
                createAlarm.StartAlarmWork(UploadSoapDetails.this);


            }
        });

    }


    private void StartOfflineWork(long id) {

        ComponentName componentName = new ComponentName(UploadSoapDetails.this, OfflineNotification.class);
        JobInfo info = new JobInfo.Builder((int) id, componentName)
//                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);

        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {

            Log.d("TAG", "Image Upload Job scheduled");


        } else {

            Log.d("TAG", "Image Upload Job scheduling failed");

            scheduler.cancel((int) id);
            Log.d("TAG", "Job cancelled");
        }

    }

    private Bitmap getImage(){

        if (imageView1.getDrawable() != null) {

            bitmap = ((BitmapDrawable) imageView1.getDrawable()).getBitmap();

        }
        return bitmap;

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            File fileImage = new File(Objects.requireNonNull(ImagePicker.Companion.getFilePath(data)));
            imageView1.setImageBitmap(BitmapFactory.decodeFile(fileImage.getAbsolutePath()));

            txtFilePath = String.valueOf(fileImage);




        } else if (resultCode == 64) {

            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }

    }

    public static String addDay(String oldDate, int numberOfDays) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(dateFormat.parse(oldDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.add(Calendar.DAY_OF_YEAR,numberOfDays);
        dateFormat= new SimpleDateFormat("dd-MMM-yyyy");
        Date newDate=new Date(c.getTimeInMillis());
        String resultDate=dateFormat.format(newDate);
        return resultDate;
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

        }

        return super.onOptionsItemSelected(item);
    }

}
