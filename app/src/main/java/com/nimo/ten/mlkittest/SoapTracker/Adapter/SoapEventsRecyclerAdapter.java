package com.nimo.ten.mlkittest.SoapTracker.Adapter;

import android.app.Activity;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Activities.SingleSoapDetails;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.OfflineNotification;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.ShowCustomToast;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapTrackerPojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class SoapEventsRecyclerAdapter extends RecyclerView.Adapter<SoapEventsRecyclerAdapter.ViewHolder>{

    private SharedPreferences preferences;

    private Context context;
    private ArrayList<SoapTrackerPojo> soapTrackerPojoArrayList;

    private DatabaseHelper databaseHelper;

    public SoapEventsRecyclerAdapter(Context context, ArrayList<SoapTrackerPojo> soapTrackerPojoArrayList) {
        this.context = context;
        this.soapTrackerPojoArrayList = soapTrackerPojoArrayList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_curing_dates, parent, false);
        SoapEventsRecyclerAdapter.ViewHolder holder = new SoapEventsRecyclerAdapter.ViewHolder(view);

        preferences = context.getSharedPreferences("Soap", Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(context);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SoapEventsRecyclerAdapter.ViewHolder holder, final int position) {


        holder.tvName.setText(soapTrackerPojoArrayList.get(position).getName());
        holder.tvDateOut.setText(soapTrackerPojoArrayList.get(position).getDateOut());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }

    @Override
    public int getItemCount() {
        return soapTrackerPojoArrayList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvDateOut;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvSoapName);
            tvDateOut = itemView.findViewById(R.id.tvDateOut);


        }
    }





}


