package com.nimo.ten.mlkittest.SoapTracker.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.Calculator;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.ShowCustomToast;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsPojo;

import java.util.ArrayList;


public class SoapLyeCalculatorRecyclerAdapter extends RecyclerView.Adapter<SoapLyeCalculatorRecyclerAdapter.ViewHolder>{

    private SharedPreferences preferences;

    private Context context;
    private ArrayList<IngredientsPojo> IngredientsPojoArrayList;

    private DatabaseHelper databaseHelper;
    private boolean isGood = true;


    public SoapLyeCalculatorRecyclerAdapter(Context context, ArrayList<IngredientsPojo> IngredientsPojoArrayList) {
        this.context = context;
        this.IngredientsPojoArrayList = IngredientsPojoArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_lye, parent, false);
        SoapLyeCalculatorRecyclerAdapter.ViewHolder holder = new SoapLyeCalculatorRecyclerAdapter.ViewHolder(view);

        preferences = context.getSharedPreferences("Soap", Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(context);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SoapLyeCalculatorRecyclerAdapter.ViewHolder holder, final int position) {

        ViewHolder viewHolder = (ViewHolder) holder;

        final int rowPos = viewHolder.getAdapterPosition();

        holder.tvNumber.setBackgroundResource(R.drawable.table_content_cell_bg);
        holder.tvIngredients.setBackgroundResource(R.drawable.table_content_cell_bg);
        holder.tvPercentage.setBackgroundResource(R.drawable.table_content_cell_bg);
        holder.tvGrams.setBackgroundResource(R.drawable.table_content_cell_bg);

        String txtIngredients = IngredientsPojoArrayList.get(rowPos).getIngredient_name();
        if (txtIngredients.length() > 15){

            holder.tvIngredients.setText(txtIngredients.substring(0, 12) + "... ");


        }else {

            holder.tvIngredients.setText(txtIngredients);

        }

        holder.tvGrams.setText(IngredientsPojoArrayList.get(rowPos).getGrams());

        holder.tvPercentage.setText(IngredientsPojoArrayList.get(rowPos).getPercentage());
        holder.tvNumber.setText(String.valueOf(rowPos + 1));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txtIngredients = IngredientsPojoArrayList.get(rowPos).getIngredient_name();
                String txtPercentage = IngredientsPojoArrayList.get(rowPos).getPercentage();
                String txtWeight = IngredientsPojoArrayList.get(rowPos).getGrams();
                String txtSoapId = IngredientsPojoArrayList.get(rowPos).getSoap_id();
                String txtId = IngredientsPojoArrayList.get(rowPos).getId();

                int position1 = rowPos;

                InputDialog(txtIngredients, txtPercentage, txtWeight, txtSoapId, txtId, position1);

            }
        });


    }

    private void InputDialog(final String txtIngredients, final String txtPercentage, final String txtWeight, final String txtSoapId, final String txtId, final int pos) {

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.alert_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);

        final EditText etIngredients = promptsView.findViewById(R.id.etIngredients);
        final EditText etPercentage = promptsView.findViewById(R.id.etPercentage);
        final EditText etWeight = promptsView.findViewById(R.id.etWeight);
        final TextView tvTitle = promptsView.findViewById(R.id.tvTitle);

        etWeight.setEnabled(false);

        etIngredients.setText(txtIngredients);
        etPercentage.setText(txtPercentage);
        etWeight.setText(txtWeight);

        tvTitle.setText("Update Substance..");

        etPercentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (!TextUtils.isEmpty(String.valueOf(s))) {

                    double text = Double.parseDouble(String.valueOf(s));
                    if (100 - text < 0) {

                        isGood = false;

                        new ShowCustomToast((Activity) context, "Your percentage is not right.");


                    }else {

                        isGood = true;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(String.valueOf(s))) {

                    double text = Double.parseDouble(String.valueOf(s));
                    if (100 - text < 0) {

                        isGood = false;

                        etPercentage.setText(txtPercentage);
                        etPercentage.setError("Your percentage cannot exceed 100%");

                    }else {

                        isGood = true;
                    }
                }

            }
        });


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

                String txtIngredients1 = etIngredients.getText().toString();
                String txtPercentage1 = etPercentage.getText().toString();

                if (isGood) {

                    if (!TextUtils.isEmpty(txtIngredients1) && !TextUtils.isEmpty(txtPercentage1) ) {

                        Calculator calculator = new Calculator();
                        double RemainingPercentage = calculator.getRemainingPercentage(txtSoapId, context);
                        Double GivenPercentage = Double.valueOf(txtPercentage1);

                        String txtWeight1 = String.valueOf(calculator.getRemainingWeight(txtSoapId, txtPercentage1, context ));

                        if (RemainingPercentage >= 0){

                            if (GivenPercentage <= RemainingPercentage) {

                                UpdateData(txtId, txtIngredients1, txtPercentage1, txtWeight1, pos);

                            }else {

                                if (GivenPercentage.equals(Double.valueOf(txtPercentage))){

                                    UpdateData(txtId, txtIngredients1, txtPercentage1, txtWeight1, pos);

                                }else if (GivenPercentage < Double.parseDouble(txtPercentage)){

                                    UpdateData(txtId, txtIngredients1, txtPercentage1, txtWeight1, pos);

                                }else {

                                    new ShowCustomToast((Activity)context, "Your Percentage is too high..");

                                    etPercentage.setText("");
                                    etPercentage.setHint("The remaining percentage is " + RemainingPercentage + " %");

                                }

                            }

                        }



                    }else {

                        if (TextUtils.isEmpty(txtIngredients1))etIngredients.setError("Ingredients cannot be empty..");
                        if (TextUtils.isEmpty(txtPercentage1))etPercentage.setError("Percentage cannot be empty..");

                    }

                }else {

                    etPercentage.setText("");
                    etPercentage.setError("Your Percentage is not correct!");

                }



            }

            private void UpdateData(String txtId, String txtIngredients1, String txtPercentage1, String txtWeight1, int pos) {

                databaseHelper.updateSoapIngredients(txtId, txtIngredients1, txtPercentage1, txtWeight1);

                new ShowCustomToast((Activity) context, "Successfully updated the ingredient");

                alertDialog.dismiss();

                IngredientsPojo ingredientsPojo = IngredientsPojoArrayList.get(pos);
                ingredientsPojo.setIngredient_name(txtIngredients1);
                ingredientsPojo.setPercentage(txtPercentage1);
                ingredientsPojo.setGrams(txtWeight1);
                notifyItemChanged(pos);

            }
        });

    }





    @Override
    public int getItemCount() {
        return IngredientsPojoArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvNumber, tvGrams, tvIngredients, tvPercentage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvGrams = itemView.findViewById(R.id.tvGrams);
            tvIngredients = itemView.findViewById(R.id.tvIngredients);
            tvPercentage = itemView.findViewById(R.id.tvPercentage);

            tvPercentage.setVisibility(View.GONE);
            
        }
    }


}

