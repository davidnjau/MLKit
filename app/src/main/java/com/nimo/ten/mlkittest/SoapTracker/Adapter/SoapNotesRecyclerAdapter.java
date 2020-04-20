package com.nimo.ten.mlkittest.SoapTracker.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Activities.SingleSoapDetails;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.ShowCustomToast;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsPojo;

import java.util.ArrayList;


public class SoapNotesRecyclerAdapter extends RecyclerView.Adapter<SoapNotesRecyclerAdapter.ViewHolder>{

    private SharedPreferences preferences;

    private Context context;
    private ArrayList<IngredientsPojo> IngredientsPojoArrayList;

    private DatabaseHelper databaseHelper;


    public SoapNotesRecyclerAdapter(Context context, ArrayList<IngredientsPojo> IngredientsPojoArrayList) {
        this.context = context;
        this.IngredientsPojoArrayList = IngredientsPojoArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_notes, parent, false);
        SoapNotesRecyclerAdapter.ViewHolder holder = new SoapNotesRecyclerAdapter.ViewHolder(view);

        preferences = context.getSharedPreferences("Soap", Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(context);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SoapNotesRecyclerAdapter.ViewHolder holder, final int position) {

        String txtNotes = IngredientsPojoArrayList.get(position).getNotes();

        if(txtNotes.length() > 100 ){

            holder.tvNotes.setText(txtNotes.substring(0, 100) + "... ");

        }else {

            holder.tvNotes.setText(txtNotes);
        }

        String txtPriority = IngredientsPojoArrayList.get(position).getPriority();

        if (txtPriority.equals("High"))holder.tvPriority.setBackgroundResource(R.drawable.ic_action_high);
        if (txtPriority.equals("Medium"))holder.tvPriority.setBackgroundResource(R.drawable.ic_action_medium);
        if (txtPriority.equals("Low"))holder.tvPriority.setBackgroundResource(R.drawable.ic_action_low);
        if (txtPriority.equals("Normal"))holder.tvPriority.setBackgroundResource(R.drawable.ic_action_normal);


        holder.tvNumber.setText(String.valueOf(position + 1));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txtNotes1 = IngredientsPojoArrayList.get(position).getNotes();
                String txtId = IngredientsPojoArrayList.get(position).getId();

                InputDialog(txtNotes1, txtId, position);

            }
        });


        holder.imgBtn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txtId = IngredientsPojoArrayList.get(position).getId();
                databaseHelper.deleteNotes(Integer.parseInt(txtId));

                new ShowCustomToast((Activity)context, "Deleted " );

                IngredientsPojoArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, IngredientsPojoArrayList.size());

            }
        });


    }

    private void InputDialog(String txtNotes1, final String txtId, final int position) {

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.update_soap_notes, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setView(promptsView);
        final EditText etInformation = promptsView.findViewById(R.id.etInformation);
        final TextView tvTitle = promptsView.findViewById(R.id.tvTitle);

        tvTitle.setText("Update Soap notes..");

        etInformation.setText(txtNotes1);

        final AlertDialog alertDialog = alertDialogBuilder.create();

        Button btn_Action = promptsView.findViewById(R.id.btn_Action);
        Button btn_Cancel = promptsView.findViewById(R.id.btn_Cancel);

        btn_Action.setText("Update");

        btn_Action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtInfo = etInformation.getText().toString();
                databaseHelper.updateSoapNotes(txtId, txtInfo);

                new ShowCustomToast((Activity) context, "Notes updated successfully..");

                alertDialog.dismiss();

                IngredientsPojo ingredientsPojo = IngredientsPojoArrayList.get(position);
                ingredientsPojo.setNotes(txtInfo);
                notifyItemChanged(position);


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





    @Override
    public int getItemCount() {
        return IngredientsPojoArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvNumber, tvNotes;
        ImageButton imgBtn_delete,tvPriority;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvPriority = itemView.findViewById(R.id.tvPriority);

            imgBtn_delete = itemView.findViewById(R.id.imgBtn_delete);

            tvNotes = itemView.findViewById(R.id.tvNotes);

        }
    }


}


