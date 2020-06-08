package com.nimo.ten.mlkittest.SoapTracker.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.SoapOilsPojo;

import java.util.ArrayList;


public class SoapMyOilsAdapter extends RecyclerView.Adapter<SoapMyOilsAdapter.ViewHolder>{

    private SharedPreferences preferences;

    private Context context;
    private ArrayList<SoapOilsPojo> soapTrackerPojoArrayList;

    private DatabaseHelper databaseHelper;

    public SoapMyOilsAdapter(Context context, ArrayList<SoapOilsPojo> soapTrackerPojoArrayList) {
        this.context = context;
        this.soapTrackerPojoArrayList = soapTrackerPojoArrayList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oil_list, parent, false);
        SoapMyOilsAdapter.ViewHolder holder = new SoapMyOilsAdapter.ViewHolder(view);

        preferences = context.getSharedPreferences("Soap", Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(context);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SoapMyOilsAdapter.ViewHolder holder, final int position) {

        final String txtOilName = soapTrackerPojoArrayList.get(position).getOil_name();
        final String txtOilId = soapTrackerPojoArrayList.get(position).getId();

        holder.tvOilName.setText(txtOilName);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Are you sure?");
                alertDialog.setMessage("This action will delete this Oil");
                alertDialog.setPositiveButton("Yes Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseHelper.deleteMySoap(txtOilId);
                        Toast.makeText(context, "Oil has been deleted.. " + txtOilName,Toast.LENGTH_LONG).show();

                        soapTrackerPojoArrayList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, soapTrackerPojoArrayList.size());

                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                alertDialog.show();

            }
        });

    }

    private void ShowDialog(final String txtOilName, final Context context) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Are you sure?");
        alertDialog.setMessage("This action will delete this Oil");
        alertDialog.setPositiveButton("Yes Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                databaseHelper.deleteMySoap(txtOilName);
                Toast.makeText(context, "Oil has been deleted.. " + txtOilName,Toast.LENGTH_LONG).show();

                notifyDataSetChanged();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        alertDialog.show();

    }

    @Override
    public int getItemCount() {
        return soapTrackerPojoArrayList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvOilName;
        private ImageButton delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOilName = itemView.findViewById(R.id.tvOilName);
            delete = itemView.findViewById(R.id.delete);


        }
    }





}


