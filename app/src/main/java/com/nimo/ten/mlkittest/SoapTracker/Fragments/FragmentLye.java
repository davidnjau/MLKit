package com.nimo.ten.mlkittest.SoapTracker.Fragments;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapLyeCalculatorRecyclerAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.Calculator;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.ShowCustomToast;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.LyeCalculatorPojo;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class FragmentLye extends Fragment {

    private SharedPreferences preferences;
    private String txtSoapId;

    private boolean isGood = true;

    public FragmentLye(){

    }

    RecyclerView recyclerView6;
    RecyclerView.LayoutManager layoutManager;

    DatabaseHelper databaseHelper;

    SoapLyeCalculatorRecyclerAdapter soapLyeCalculatorRecyclerAdapter;
    List<IngredientsPojo> soapTrackerPojoArrayList;

    private LinearLayout myview;

    private Calculator calculator;
    TextView tvNumber, tvGrams, tvIngredients, tvPercentage;
    TextView tvNumber1, tvGrams1, tvIngredients1, tvPercentage1;
    TextView tvNumber2, tvGrams2, tvIngredients2, tvPercentage2;

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lye, container, false);


        databaseHelper = new DatabaseHelper(getActivity());

        calculator = new Calculator();

        tvNumber = view.findViewById(R.id.tvNumber);
        tvGrams = view.findViewById(R.id.tvGrams);
        tvIngredients = view.findViewById(R.id.tvIngredients);
        tvPercentage = view.findViewById(R.id.tvPercentage);

        tvNumber1 = view.findViewById(R.id.tvNumber1);
        tvGrams1 = view.findViewById(R.id.tvGrams1);
        tvIngredients1 = view.findViewById(R.id.tvIngredients1);
        tvPercentage1 = view.findViewById(R.id.tvPercentage1);

        tvNumber2 = view.findViewById(R.id.tvNumber2);
        tvGrams2 = view.findViewById(R.id.tvGrams2);
        tvIngredients2 = view.findViewById(R.id.tvIngredients2);
        tvPercentage2 = view.findViewById(R.id.tvPercentage2);

        recyclerView6 = view.findViewById(R.id.recyclerView6);

        myview = view.findViewById(R.id.myview);


        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputDialog();
            }
        });



        return view;
    }

    public void GetDefaultDetails(String txtSoapId) {


        tvNumber.setBackgroundResource(R.drawable.table_header_cell_bg);
        tvIngredients.setBackgroundResource(R.drawable.table_header_cell_bg);
        tvPercentage.setBackgroundResource(R.drawable.table_header_cell_bg);
        tvGrams.setBackgroundResource(R.drawable.table_header_cell_bg);

        tvNumber1.setBackgroundResource(R.drawable.table_content_cell_bg);
        tvIngredients1.setBackgroundResource(R.drawable.table_content_cell_bg);
        tvPercentage1.setBackgroundResource(R.drawable.table_content_cell_bg);
        tvGrams1.setBackgroundResource(R.drawable.table_content_cell_bg);

        tvNumber2.setBackgroundResource(R.drawable.table_content_cell_bg);
        tvIngredients2.setBackgroundResource(R.drawable.table_content_cell_bg);
        tvPercentage2.setBackgroundResource(R.drawable.table_content_cell_bg);
        tvGrams2.setBackgroundResource(R.drawable.table_content_cell_bg);

        tvNumber.setText("#");
        tvIngredients.setText("Substance");
        tvPercentage.setText("%");
        tvGrams.setText("grams");

        //Add
        tvNumber.setTextColor(Color.parseColor("#F5FFFFFF"));
        tvIngredients.setTextColor(Color.parseColor("#F5FFFFFF"));
        tvPercentage.setTextColor(Color.parseColor("#F5FFFFFF"));
        tvGrams.setTextColor(Color.parseColor("#F5FFFFFF"));

        tvNumber1.setText("1");
        tvIngredients1.setText("NaOH");
        tvPercentage1.setText("33.3");


        tvNumber2.setText("2");
        tvIngredients2.setText("Liquid(s)");
        tvPercentage2.setText("66.67");

    }

    private void InputDialog() {

        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.alert_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptsView);

        final EditText etSubstance = promptsView.findViewById(R.id.etIngredients);
        final EditText etPercentage = promptsView.findViewById(R.id.etPercentage);
        final EditText etWeight = promptsView.findViewById(R.id.etWeight);

        final TextView tvTitle = promptsView.findViewById(R.id.tvTitle);

        etSubstance.setHint("Other liquids");

        tvTitle.setText("Add substance");

        double RemainingPercentage = calculator.getLiquidRemainingPercentage(txtSoapId, getActivity());
        if (RemainingPercentage >= 0)etPercentage.setHint("The remaining percentage is " + RemainingPercentage + " %");

        etWeight.setEnabled(false);

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

                        new ShowCustomToast(getActivity(), "Your percentage is not right.");


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

                        etPercentage.setText("");
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

                String txtSubstance = etSubstance.getText().toString();
                String txtPercentage = etPercentage.getText().toString();

                if (isGood) {

                    if (!TextUtils.isEmpty(txtSubstance) && !TextUtils.isEmpty(txtPercentage) ) {

                        double RemainingPercentage = calculator.getLiquidRemainingPercentage(txtSoapId, getActivity());
                        Double GivenPercentage = Double.valueOf(txtPercentage);

                        if (RemainingPercentage >= 0){

                            if (GivenPercentage <= RemainingPercentage) {

                                String txtSoapWeight = String.valueOf(calculator.getRemainingLiquidPercentageWeight(txtSoapId, txtPercentage, getActivity() ));

                                databaseHelper.AddLye(txtSubstance, txtSoapId, txtPercentage, txtSoapWeight);

                                new ShowCustomToast(getActivity(), "Successfully added the Substance");

                                alertDialog.dismiss();

                                StartRecyclerView(txtSoapId);


                            }else {

                                new ShowCustomToast(getActivity(), "Your Percentage is too high..");

                                etPercentage.setText("");
                                etPercentage.setHint("The remaining percentage is " + RemainingPercentage + " %");

                            }

                        }


                    }else {

                        if (TextUtils.isEmpty(txtSubstance))etSubstance.setError("Substance used cannot be empty..");
                        if (TextUtils.isEmpty(txtPercentage))etPercentage.setError("Percentage cannot be empty..");

                    }

                }else {

                    etPercentage.setText("");
                    etPercentage.setError("Your Percentage is not correct!");

                }

            }
        });
        
    }

    @Override
    public void onStart() {
        super.onStart();

        preferences = getActivity().getSharedPreferences("Soap", MODE_PRIVATE);
        txtSoapId = preferences.getString("soap_id", null);

        GetDefaultDetails(txtSoapId);
        StartRecyclerView(txtSoapId);
    }

    private void getSoapDetails(String txtSoapId) {

        LyeCalculatorPojo lyeCalculatorPojo = new LyeCalculatorPojo();
        lyeCalculatorPojo= calculator.getNaohSubstance(txtSoapId, getActivity());

        tvGrams1.setText(String.valueOf(lyeCalculatorPojo.getTxtNaOH()));
        tvGrams2.setText(String.valueOf(lyeCalculatorPojo.getLiquids()));



    }

    private void StartRecyclerView(String txtSoapId) {

        recyclerView6.setLayoutManager(new LinearLayoutManager(getActivity()));

        soapTrackerPojoArrayList = databaseHelper.getSoapLye(txtSoapId);

        soapLyeCalculatorRecyclerAdapter = new SoapLyeCalculatorRecyclerAdapter(getActivity(), (ArrayList<IngredientsPojo>) soapTrackerPojoArrayList);
        recyclerView6.setAdapter(soapLyeCalculatorRecyclerAdapter);

        if (soapLyeCalculatorRecyclerAdapter.getItemCount() == 0){

            System.out.println("-*-*-*>0 " + soapLyeCalculatorRecyclerAdapter.getItemCount());

            myview.setVisibility(View.VISIBLE);
            recyclerView6.setVisibility(View.GONE);

        }else {

            System.out.println("-*-*-*<0 " + soapLyeCalculatorRecyclerAdapter.getItemCount());

            myview.setVisibility(View.GONE);
            recyclerView6.setVisibility(View.VISIBLE);

        }


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser){

            getSoapDetails(txtSoapId);
            StartRecyclerView(txtSoapId);

        }
    }
}
