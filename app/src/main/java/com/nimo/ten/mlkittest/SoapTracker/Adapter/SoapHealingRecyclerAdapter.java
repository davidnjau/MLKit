package com.nimo.ten.mlkittest.SoapTracker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Activities.SingleSoapDetails;
import com.nimo.ten.mlkittest.SoapTracker.Activities.Soap_ingredients_notes;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.OfflineNotification;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapTrackerPojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class SoapHealingRecyclerAdapter extends RecyclerView.Adapter<SoapHealingRecyclerAdapter.ViewHolder>{

    private SharedPreferences preferences;

    private Context context;
    private ArrayList<SoapTrackerPojo> soapTrackerPojoArrayList;

    private DatabaseHelper databaseHelper;
    private String txtElapsedDays;


    public SoapHealingRecyclerAdapter(Context context, ArrayList<SoapTrackerPojo> soapTrackerPojoArrayList) {
        this.context = context;
        this.soapTrackerPojoArrayList = soapTrackerPojoArrayList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_soap, parent, false);
        SoapHealingRecyclerAdapter.ViewHolder holder = new SoapHealingRecyclerAdapter.ViewHolder(view);

        preferences = context.getSharedPreferences("Soap", Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(context);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SoapHealingRecyclerAdapter.ViewHolder holder, final int position) {

        OfflineNotification offlineNotification = new OfflineNotification();

        holder.tvName.setText(soapTrackerPojoArrayList.get(position).getName());
        holder.tvDateIn.setText(soapTrackerPojoArrayList.get(position).getDateIn());
        holder.tvDateOut.setText(soapTrackerPojoArrayList.get(position).getDateOut());


        try {

            long dateOut = offlineNotification.getTimeInMillis(soapTrackerPojoArrayList.get(position).getDateOut());
            long date_In = offlineNotification.getTimeInMillis(soapTrackerPojoArrayList.get(position).getDateIn());

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String CurrentDate = df.format(date_In);

            long dateIn = offlineNotification.getTimeInMillis(CurrentDate);

            long remainingDays = TimeUnit.DAYS.convert(dateOut - dateIn, TimeUnit.MILLISECONDS);

            if (remainingDays > 0) {

                holder.tvRemainingDays.setText(String.valueOf(remainingDays) + " d");

            }

            holder.tvRemainingDays.setBackgroundResource(R.drawable.badge_circular_before_due);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvRemainingDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtDaysRem = holder.tvRemainingDays.getText().toString();
                String txtSoapName = holder.tvName.getText().toString();

                Toast.makeText(context, getElapsedDays(txtDaysRem) + " remaining for " + txtSoapName + " to heal. ", Toast.LENGTH_SHORT).show();

            }
        });


        byte[] image = soapTrackerPojoArrayList.get(position).getPhoto();

        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        holder.imageView.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("soap_id", String.valueOf(soapTrackerPojoArrayList.get(position).getId()));
                editor.apply();

                Intent intent = new Intent(context, SingleSoapDetails.class);
                context.startActivity(intent);
            }
        });


    }




    @Override
    public int getItemCount() {
        return soapTrackerPojoArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvDateIn, tvDateOut, tvRemainingDays;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDateIn = itemView.findViewById(R.id.tvDateIn);
            tvDateOut = itemView.findViewById(R.id.tvDateOut);
            tvRemainingDays = itemView.findViewById(R.id.tvRemainingDays);

            imageView = itemView.findViewById(R.id.imageView);


        }
    }


    private String getElapsedDays(String txtString){

        if (txtString.contains("d")){

            if (txtString.length() <= 3){

                //These are days from day 1 - 9 e.g. 1 d
                String newS = txtString.substring(0,1);
                if (newS.equals("1")){

                    txtElapsedDays = newS + " day";

                }else {

                    txtElapsedDays = newS + " days";

                }

            }else {

                String newS = txtString.substring(0,2);
                txtElapsedDays = newS + " days";


            }




        }

        return txtElapsedDays;
    }

}


