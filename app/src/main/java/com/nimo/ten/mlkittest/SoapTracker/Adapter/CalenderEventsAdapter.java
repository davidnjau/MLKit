package com.nimo.ten.mlkittest.SoapTracker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapTrackerPojo;

import java.util.ArrayList;
import java.util.List;

public class CalenderEventsAdapter extends RecyclerView.Adapter<CalenderEventsAdapter.ViewHolder> {

    private Context context;
    private List<String> soapTrackerPojoArrayList;

    public CalenderEventsAdapter(Context context, List<String> soapTrackerPojoArrayList) {
        this.context = context;
        this.soapTrackerPojoArrayList = soapTrackerPojoArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_curing_dates, parent, false);
        CalenderEventsAdapter.ViewHolder holder = new CalenderEventsAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String eventName = soapTrackerPojoArrayList.get(position);

        holder.tvSoapName.setText(eventName);



    }

    @Override
    public int getItemCount() {
        return soapTrackerPojoArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvSoapName;
        LinearLayout linearDates;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSoapName = itemView.findViewById(R.id.tvSoapName);
            linearDates = itemView.findViewById(R.id.linearDates);

            linearDates.setVisibility(View.GONE);

        }
    }

}
