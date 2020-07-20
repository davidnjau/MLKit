package com.nimo.ten.mlkittest.SoapTracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TaskStackBuilder;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.CheckOnlineDb;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.CreateAlarm;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.FetchDataBase;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.GetText;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.OfflineNotification;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.ShowCustomToast;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.GetWeeksDaysPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsNotesPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.ShareContentPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapTrackerPojo;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SingleSoapDetails extends AppCompatActivity {

    private ImageView imageView;
    private TextView tvSoapName, tvDateIn, tvDateOut, tvDurationTaken, tvSoapCondition;
    private SharedPreferences preferences;
    private String Soap_id;

    private DatabaseHelper databaseHelper;
    private long dateIn, dateOut;

    private long numberOfWeeks;
    private long numberOfDays;

    private String txtWeek, txtDay;

    private String txtDuration, txtPicPath, txtShareInfoName;
    private byte[] image;
    private SimpleDateFormat df;
    private String txtDate;

    private String txtSoapIngredient = "";
    private String txtSoapNotes = "";
    private String txtLye = "";

    private String txtSoapIngredient_Weight = "";
    private String txtSoapLye_Weight = "";

    List<String> myIngredients = new ArrayList<>();
    List<String> myLye = new ArrayList<>();
    List<String> myNotes = new ArrayList<>();


    ArrayList<IngredientsNotesPojo> ingredientsNotesPojoArrayList = new ArrayList<>();
    private FetchDataBase fetchDataBase;
    private static final String KEY_INGREDIENTS = "ingredients";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_SUBSTANCE = "substance";

    private static final String TABLE_SOAP_INGREDIENTS = "soap_ingredients";
    private static final String KEY_SOAP_ID = "soap_id";
    private static final String KEY_NOTES = "notes";
    private static final String TABLE_SOAP_NOTES = "soap_notes";
    private static final String TABLE_SOAP_LYE = "soap_lye";


    private List<String> myShareIngredients = new ArrayList<>();
    private List<String> myShareNotes = new ArrayList<>();
    private List<String> myShareLye = new ArrayList<>();

    private Bitmap bitmap;
    private String txtNaoh, txtLiquids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_soap_details);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseHelper = new DatabaseHelper(this);
        fetchDataBase = new FetchDataBase();

        preferences = getApplicationContext().getSharedPreferences("Soap", MODE_PRIVATE);

        imageView = findViewById(R.id.imageView);

        df = new SimpleDateFormat("dd-MMM-yyyy");

        tvSoapName = findViewById(R.id.tvSoapName);
        tvDateIn = findViewById(R.id.tvDateIn);
        tvDateOut = findViewById(R.id.tvDateOut);
        tvDurationTaken = findViewById(R.id.tvDurationTaken);
        tvSoapCondition = findViewById(R.id.tvSoapCondition);

        findViewById(R.id.btn_MoreDetails).setOnClickListener(v -> {


//            Intent intent = new Intent(getApplicationContext(), SoapRecipe.class);
//            startActivity(intent);

        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        Soap_id = preferences.getString("soap_id", null);

        LoadData(Soap_id);

//        CheckOnlineDb checkOnlineDb = new CheckOnlineDb();
//        checkOnlineDb.getDataFromDb(SingleSoapDetails.this);


    }

    private void LoadData(String soap_id) {

        SoapTrackerPojo soapTrackerPojo = databaseHelper.getSoapInformation(soap_id);
        OfflineNotification offlineNotification = new OfflineNotification();
        GetWeeksDaysPojo getWeeksDaysPojo = new GetWeeksDaysPojo();

        if (soap_id != null){

            image = soapTrackerPojo.getPhoto();
            bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            imageView.setImageBitmap(bitmap);

            try {

                dateIn = offlineNotification.getTimeInMillis(soapTrackerPojo.getDateIn());
                dateOut = offlineNotification.getTimeInMillis(soapTrackerPojo.getDateOut());

                long remainingDays = TimeUnit.DAYS.convert(( dateOut - dateIn ) , TimeUnit.MILLISECONDS) ;

                if (remainingDays >= 0){


                    numberOfWeeks = getWeeksAndDays(remainingDays).getNumberOfWeeks();
                    numberOfDays = getWeeksAndDays(remainingDays).getNumberOfDays();

                    txtDuration = numberOfWeeks + getWeeksDaysStrings(numberOfWeeks, numberOfDays).getTxtWeeks()
                            +" "
                            + numberOfDays + getWeeksDaysStrings(numberOfWeeks, numberOfDays).getTxtDays();

                    tvDurationTaken.setText(txtDuration);

                }else {

                    long remainingPositiveDays = remainingDays * -1;

                    numberOfWeeks = getWeeksAndDays(remainingPositiveDays).getNumberOfWeeks();
                    numberOfDays = getWeeksAndDays(remainingPositiveDays).getNumberOfDays();

                    txtDuration = numberOfWeeks + getWeeksDaysStrings(numberOfWeeks, numberOfDays).getTxtWeeks()
                            +" "
                            + numberOfDays + getWeeksDaysStrings(numberOfWeeks, numberOfDays).getTxtDays();

                    tvDurationTaken.setText(txtDuration);
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }

            tvSoapName.setText(soapTrackerPojo.getName());
            tvDateIn.setText(soapTrackerPojo.getDateIn());
            tvDateOut.setText(soapTrackerPojo.getDateOut());
            tvSoapCondition.setText(soapTrackerPojo.getCondition());

            txtShareInfoName = soapTrackerPojo.getName() + "\n";

            txtPicPath = soapTrackerPojo.getPicPath();


        }

    }

    private ShareContentPojo LoadIngredients(String soap_id) {

        txtSoapIngredient_Weight = "With the following ingredients: ";
        myIngredients.add(txtSoapIngredient_Weight);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_INGREDIENTS +" WHERE " + KEY_SOAP_ID + " = '"+soap_id+"'";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null){

            if (c.moveToFirst()) {

                do {

                    String txtIngredient = c.getString(c.getColumnIndex(KEY_INGREDIENTS));
                    String txtWeight = c.getString(c.getColumnIndex(KEY_WEIGHT));

                    txtSoapIngredient_Weight = txtWeight + " grams of " + txtIngredient;

                    myIngredients.add(txtSoapIngredient_Weight);


                } while (c.moveToNext());

                c.close();

            }
        }

        return new ShareContentPojo(myIngredients, null, null);

    }

    private ShareContentPojo LoadLye(String soap_id) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_LYE +" WHERE " + KEY_SOAP_ID + " = '"+soap_id+"'";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null){

            String txtLye1 = "With the following Lye mixture:";
            myLye.add(txtLye1);

            FetchDataBase fetchDataBase = new FetchDataBase();
            txtNaoh = fetchDataBase.getLye_LiquidWeights(Soap_id, SingleSoapDetails.this).get(0).getNaOh() + " grams of " + "NaOH";
            txtLiquids = fetchDataBase.getLye_LiquidWeights(Soap_id, SingleSoapDetails.this).get(0).getLiquids()+ " grams of " + "Other Liquids" ;

            myLye.add(txtNaoh);
            myLye.add(txtLiquids);

            if (c.moveToFirst()) {

                do {

                    String txtLye = c.getString(c.getColumnIndex(KEY_SUBSTANCE));
                    String txtWeight = c.getString(c.getColumnIndex(KEY_WEIGHT));

                    txtSoapLye_Weight = " > " + txtWeight + " grams of " + txtLye;

                    myLye.add(txtSoapLye_Weight);


                } while (c.moveToNext());

                c.close();

            }
        }

        return new ShareContentPojo(null, null, myLye);

    }

    private ShareContentPojo LoadNotes(String soap_id) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String selectQueryNotes = "SELECT * FROM " + TABLE_SOAP_NOTES +" WHERE " + KEY_SOAP_ID + " = '"+soap_id+"'";
        Cursor cNotes = db.rawQuery(selectQueryNotes, null);

        if (cNotes != null){

            String txtNewNotes = "And the following notes: ";
            myNotes.add(txtNewNotes);

            if (cNotes.moveToFirst()) {


                do {

                    String txtNotes = cNotes.getString(cNotes.getColumnIndex(KEY_NOTES));

                    myNotes.add(txtNotes);

                } while (cNotes.moveToNext());

                cNotes.close();

            }
        }




        return new ShareContentPojo(null, myNotes, null);

    }

    private GetWeeksDaysPojo getWeeksAndDays(long daysElapsed){

        for (long i = 0; i < 52; i++){

            long result = i * 7;

            if (result < daysElapsed){

                numberOfWeeks = i;
                numberOfDays = daysElapsed - result;

            }

        }

        return new GetWeeksDaysPojo(numberOfWeeks, numberOfDays, null, null);

    }

    private GetWeeksDaysPojo getWeeksDaysStrings(long week, long days){

        if (week == 0)txtWeek = " weeks";
        if (week == 1)txtWeek = " week";
        if (week > 1)txtWeek = " weeks";

        if (days == 0) txtDay = " days";
        if (days == 1) txtDay = " day";
        if (days > 0) txtDay = " days";

        return new GetWeeksDaysPojo(0, 0, txtWeek, txtDay);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(getApplicationContext(), SoapHeal_Healing.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.ic_share) {
//
////            InputDialog();
//
//        }else
        if (id == R.id.ic_delete) {

            DeleteDialog();

        }else if (id == R.id.icon_calender){

            startActivity(new Intent(getApplicationContext(), Calender.class));

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

    private void DeleteDialog() {

        final GetText getEditText = new GetText();

        LayoutInflater li = LayoutInflater.from(SingleSoapDetails.this);
        View promptsView = li.inflate(R.layout.delete_dialog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SingleSoapDetails.this);

        alertDialogBuilder.setView(promptsView);

        TextView TvSoapName = promptsView.findViewById(R.id.tvSoapName);
        TextView TvDateIn = promptsView.findViewById(R.id.tvDateIn);
        TextView TvDateOut = promptsView.findViewById(R.id.tvDateOut);
        TextView TvDurationTaken = promptsView.findViewById(R.id.tvDurationTaken);
        TextView TvSoapCondition = promptsView.findViewById(R.id.tvSoapCondition);

        TvSoapName.setText(getEditText.getText(tvSoapName));
        TvDateIn.setText(getEditText.getText(tvDateIn));
        TvDateOut.setText(getEditText.getText(tvDateOut));
        TvDurationTaken.setText(getEditText.getText(tvDurationTaken));
        TvSoapCondition.setText(getEditText.getText(tvSoapCondition));

        final AlertDialog alertDialog = alertDialogBuilder.create();

        Button btn_Action = promptsView.findViewById(R.id.btn_Action);
        Button btn_Cancel = promptsView.findViewById(R.id.btn_Cancel);

        btn_Action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ShowCustomToast(SingleSoapDetails.this, "Deleted " + getEditText.getText(tvSoapName));

                databaseHelper.deleteSoap(Integer.parseInt(Soap_id));

                alertDialog.dismiss();

                Intent intent = new Intent(getApplicationContext(), SoapHeal_Healing.class);
                startActivity(intent);
                finish();

                preferences.edit().remove("soap_id").apply();


            }
        });

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });


        alertDialog.show();


    }

    private void InputDialog() {

        LayoutInflater li = LayoutInflater.from(SingleSoapDetails.this);
        View promptsView = li.inflate(R.layout.add_share_information, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SingleSoapDetails.this);

        alertDialogBuilder.setView(promptsView);
        final EditText etInformation = promptsView.findViewById(R.id.etInformation);

        final CheckBox checkboxIngredients = promptsView.findViewById(R.id.checkboxIngredients);
        final CheckBox checkboxNotes = promptsView.findViewById(R.id.checkboxNotes);
        final CheckBox checkboxLye = promptsView.findViewById(R.id.checkboxLye);

        myShareIngredients.clear();
        myShareNotes.clear();
        myLye.clear();

        txtSoapIngredient = "";
        txtSoapNotes = "";
        txtLye = "";

        txtNaoh = "";
        txtLiquids = "";

        checkboxLye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                    myShareLye = LoadLye(Soap_id).getMyLye();

                }else {

                    txtLye = "";
                    myShareLye.clear();

                }

            }
        });


        checkboxIngredients.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                    myShareIngredients = LoadIngredients(Soap_id).getMyIngredients();

                }else {

                    txtSoapIngredient = "";
                    myShareIngredients.clear();

                }


            }
        });

        checkboxNotes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                    myShareNotes = LoadNotes(Soap_id).getMyNotes();

                }else {

                    txtSoapNotes = "";
                    myShareNotes.clear();

                }


            }
        });

        final AlertDialog alertDialog = alertDialogBuilder.create();

        Button btn_Action = promptsView.findViewById(R.id.btn_Action);
        Button btn_Cancel = promptsView.findViewById(R.id.btn_Cancel);

        btn_Action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkboxIngredients.isChecked()){

                    for (int i = 0; i< myShareIngredients.size(); i++){

                        String txtIngredients1 = myShareIngredients.get(i) + "\n";
                        txtSoapIngredient = txtSoapIngredient + txtIngredients1;

                    }

                }else txtSoapIngredient = "";

                if (checkboxNotes.isChecked()){

                    for (int i = 0; i< myShareNotes.size(); i++){

                        String txtNotes = myShareNotes.get(i) + "\n";
                        txtSoapNotes = txtSoapNotes + txtNotes;

                    }

                }else txtSoapNotes = "";

                if (checkboxLye.isChecked()){

                    for (int i = 0; i< myShareLye.size(); i++){

                        String txtLye1 = myShareLye.get(i) + "\n";
                        txtLye = txtLye + txtLye1;

                    }

                }else txtSoapLye_Weight = "";

                String txtInfo = etInformation.getText().toString();

                if (!TextUtils.isEmpty(txtInfo)){

                    ShareInfo(txtInfo);

                }else {

                    ShareInfo("");

                }

                alertDialog.dismiss();

            }
        });

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });


        alertDialog.show();



    }

    private void ShareInfo(String NewInfo) {

        String txtIgAccount = "https://instagram.com/nimonaturals?igshid=lktxo918p0wp";

        String txtNewInfo = txtShareInfoName + "\n" + NewInfo + "\n"

                + "\n" + txtSoapIngredient + "\n"
                + "\n" + txtLye + "\n"
                + "\n" + txtSoapNotes + "\n"

                + "\n" + "Follow us on IG: " + "\n" + txtIgAccount +
                  "\n" + "Shared via Soap Tracker.";

        String savedImageURL =  MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "yourTitle" , "yourDescription");
        Uri savedImageURI = Uri.parse(savedImageURL);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, txtNewInfo);
        shareIntent.putExtra(Intent.EXTRA_STREAM, savedImageURI);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(Intent.createChooser(shareIntent, "Share to "));

    }


    public void CalenderView(View view) {

        switch (view.getId()){

            case R.id.calender_DateIn:

                txtDate = "DateIn";

                break;

            case R.id.calender_DateOut:

                txtDate = "DateOut";

                break;



        }

        CalenderDialog(txtDate);

    }

    private void CalenderDialog(final String txtDate) {

        LayoutInflater li = LayoutInflater.from(SingleSoapDetails.this);
        View promptsView = li.inflate(R.layout.calender_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SingleSoapDetails.this);
        alertDialogBuilder.setView(promptsView);

        TextView tvInfo = promptsView.findViewById(R.id.tvInfo);

        final MaterialCalendarView calendarView = promptsView.findViewById(R.id.calendarView);

        if (txtDate.equals("DateIn")) {

            calendarView.state().edit()
                    .setMaximumDate(CalendarDay.today())
                    .commit();

            tvInfo.setText("Choose the Date when the soap was prepared.");
        }
        if (txtDate.equals("DateOut")){

            calendarView.state().edit()
                    .setMinimumDate(CalendarDay.today())
                    .commit();

            tvInfo.setText("Choose the Date when the soap cured.");


        }
        Date c = Calendar.getInstance().getTime();
        calendarView.setCurrentDate(c);

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

                databaseHelper.updateDate(txtDate, startSelectedDate, Soap_id);

                CreateAlarm createAlarm = new CreateAlarm();
                createAlarm.StartAlarmWork(SingleSoapDetails.this);

                new ShowCustomToast(SingleSoapDetails.this, "Date updated..");

                alertDialog.dismiss();

                LoadData(Soap_id);

            }
        });

    }

}
