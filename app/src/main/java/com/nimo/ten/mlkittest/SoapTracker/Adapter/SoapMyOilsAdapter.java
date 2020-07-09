package com.nimo.ten.mlkittest.SoapTracker.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.Fragments.FragmentOilMain;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.Calculator;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.SoapOilsPojo;

import java.util.ArrayList;

public class SoapMyOilsAdapter extends RecyclerView.Adapter<SoapMyOilsAdapter.ViewHolder>{

    private SharedPreferences preferences;

    private Context context;
    private ArrayList<SoapOilsPojo> soapTrackerPojoArrayList;

    private DatabaseHelper databaseHelper;
    private Calculator calculator;

    private FragmentOilMain fragmentOilMain;

    private String txtSoapId;

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
        txtSoapId = preferences.getString("recipe_id", null);


        databaseHelper = new DatabaseHelper(context);
        calculator = new Calculator();
        fragmentOilMain = new FragmentOilMain();

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SoapMyOilsAdapter.ViewHolder holder, final int position) {

        final String txtOilName = soapTrackerPojoArrayList.get(position).getOil_name();
        final String txtOilId = soapTrackerPojoArrayList.get(position).getId();
        final String txtOilWeight = soapTrackerPojoArrayList.get(position).getOilWeight();

        holder.tvNumber.setText(String.valueOf(position +1));
        holder.tvOilName.setText(txtOilName);
        holder.etOilWeight.setText(txtOilWeight);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Are you sure?");
                alertDialog.setMessage("This action will delete this Oil");
                alertDialog.setPositiveButton("Yes Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseHelper.deleteMySoap(txtOilId, context);
                        Toast.makeText(context, txtOilName+" Oil has been deleted.. " ,Toast.LENGTH_LONG).show();

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

        holder.etOilWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                databaseHelper.updateMyOilWeight(txtOilId, String.valueOf(editable));

                if (!TextUtils.isEmpty(String.valueOf(editable))){

                    calculator.getSapofinication(txtSoapId, txtOilId, String.valueOf(editable), context);


                }



            }
        });



    }

    @Override
    public int getItemCount() {
        return soapTrackerPojoArrayList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvOilName, tvNumber;
        private ImageButton delete;
        private EditText etOilWeight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOilName = itemView.findViewById(R.id.tvOilName);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            delete = itemView.findViewById(R.id.delete);
            etOilWeight = itemView.findViewById(R.id.etOilWeight);

        }
    }





}


