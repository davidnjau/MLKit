package com.nimo.ten.mlkittest.SoapTracker.Fragments;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nimo.ten.mlkittest.R;

import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapIngredientsRecyclerAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.Calculator;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.ShowCustomToast;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsPojo;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class FragmentIngredients extends Fragment {

    private SharedPreferences preferences;
    private String txtSoapId;

    private boolean isGood = true;

    public FragmentIngredients(){

    }

    RecyclerView recyclerView6;
    RecyclerView.LayoutManager layoutManager;

    DatabaseHelper databaseHelper;

    SoapIngredientsRecyclerAdapter soapDetailsRecyclerAdapter;
    List<IngredientsPojo> soapTrackerPojoArrayList;

    private LinearLayout myview;

    private EditText etTotalWeight;

    private Calculator calculator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        recyclerView6 = view.findViewById(R.id.recyclerView6);
        databaseHelper = new DatabaseHelper(getActivity());


        calculator = new Calculator();

        etTotalWeight = view.findViewById(R.id.etTotalWeight);

        view.findViewById(R.id.btnAddWeight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtWeight = etTotalWeight.getText().toString();

                Double Weight = calculator.getTotalWeight(txtSoapId, getActivity());

                if (!String.valueOf(Weight).equals("0.0")){

                    //Start the recycler view

                    if (!TextUtils.isEmpty(txtWeight)){

                        if (!calculator.UpdateAllGrams(txtSoapId, txtWeight, getActivity())){

                            etTotalWeight.setText(String.valueOf(calculator.getTotalWeight(txtSoapId, getActivity())));
                            new ShowCustomToast(getActivity(), "Done.");

                        }else new ShowCustomToast(getActivity(), "Done.");

                    }else etTotalWeight.setError("Please add a valid weight.");

                }else {


                    new ShowCustomToast(getActivity(), "Done.");
                    calculator.UpdateAllGrams(txtSoapId, txtWeight, getActivity());

                }

                StartRecyclerView(txtSoapId);



            }
        });

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

    private void InputDialog() {

        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.alert_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptsView);

        final EditText etIngredients = promptsView.findViewById(R.id.etIngredients);
        final EditText etPercentage = promptsView.findViewById(R.id.etPercentage);
        final EditText etWeight = promptsView.findViewById(R.id.etWeight);

        double RemainingPercentage = calculator.getRemainingPercentage(txtSoapId, getActivity());
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

                String txtIngredients = etIngredients.getText().toString();
                String txtPercentage = etPercentage.getText().toString();

                if (isGood) {

                    if (!TextUtils.isEmpty(txtIngredients) && !TextUtils.isEmpty(txtPercentage) ) {

                        double RemainingPercentage = calculator.getRemainingPercentage(txtSoapId, getActivity());
                        Double GivenPercentage = Double.valueOf(txtPercentage);

                        if (RemainingPercentage >= 0){

                            if (GivenPercentage <= RemainingPercentage) {

                                String txtSoapWeight = String.valueOf(calculator.getRemainingWeight(txtSoapId, txtPercentage, getActivity() ));

                                databaseHelper.AddIngredients(txtIngredients, txtSoapId, txtPercentage, txtSoapWeight);

                                new ShowCustomToast(getActivity(), "Successfully added an ingredient");

                                alertDialog.dismiss();

                                StartRecyclerView(txtSoapId);

                            }else {

                                new ShowCustomToast(getActivity(), "Your Percentage is too high..");

                                etPercentage.setText("");
                                etPercentage.setHint("The remaining percentage is " + RemainingPercentage + " %");

                            }

                        }


                    }else {

                        if (TextUtils.isEmpty(txtIngredients))etIngredients.setError("Ingredients cannot be empty..");
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

        StartRecyclerView(txtSoapId);
    }




    public void StartRecyclerView(String txtSoapId) {

        recyclerView6.setLayoutManager(new LinearLayoutManager(getActivity()));

        soapTrackerPojoArrayList = databaseHelper.getSoapIngredients(txtSoapId);

        soapDetailsRecyclerAdapter = new SoapIngredientsRecyclerAdapter(getActivity(), (ArrayList<IngredientsPojo>) soapTrackerPojoArrayList);
        recyclerView6.setAdapter(soapDetailsRecyclerAdapter);

        if (soapDetailsRecyclerAdapter.getItemCount() == 1){

            myview.setVisibility(View.VISIBLE);
            recyclerView6.setVisibility(View.GONE);

        }else {

            myview.setVisibility(View.GONE);
            recyclerView6.setVisibility(View.VISIBLE);

        }

        Double txtWeight = calculator.getTotalWeight(txtSoapId, getActivity());
        etTotalWeight.setText(String.valueOf(txtWeight));





    }



}
