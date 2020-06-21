package com.nimo.ten.mlkittest.SoapTracker.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Activities.Soap_ingredients_notes;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.MyOilsAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapMyOilsAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapOilsAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.Calculator;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.SoapOilsPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapLyeLiquidsPojo;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class FragmentOilMain extends Fragment {

    public FragmentOilMain(){

    }

    private RecyclerView recyclerView;
    private SoapMyOilsAdapter oilsAdapter;
    private ArrayList<SoapOilsPojo> soapLyeLiquidsPojoArrayList = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private SharedPreferences preferences;
    private String Soap_id;

    private TextView tvNaoHRequired, tvTotalOilWeight, tvOils;
    private Calculator calculator;
    private Button btnCalculate;

    private EditText etEssentiaOilName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_oil, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        preferences = getActivity().getSharedPreferences("Soap", MODE_PRIVATE);

        tvOils = view.findViewById(R.id.tvOils);
        tvNaoHRequired = view.findViewById(R.id.tvNaoHRequired);
        tvTotalOilWeight = view.findViewById(R.id.tvTotalOilWeight);
        btnCalculate = view.findViewById(R.id.btnCalculate);
        etEssentiaOilName = view.findViewById(R.id.etEssentiaOilName);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AssignData();


                Intent intent = new Intent(getActivity(), Soap_ingredients_notes.class);
                startActivity(intent);

            }
        });

        etEssentiaOilName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                AssignData();



            }
        });

        calculator = new Calculator();

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        StartRecyclerView();
    }

    private void StartRecyclerView() {
        Soap_id = preferences.getString("soap_id", null);

        recyclerView.setHasFixedSize(true);

        databaseHelper = new DatabaseHelper(getActivity());

        soapLyeLiquidsPojoArrayList = databaseHelper.getMySoapOils(Soap_id);
        oilsAdapter = new SoapMyOilsAdapter(getActivity(), soapLyeLiquidsPojoArrayList);

        recyclerView.setAdapter(oilsAdapter);

        AssignData();

        tvOils.setText("Oils selected");


    }

    public void AssignData(){

        tvNaoHRequired.setText(String.valueOf(calculator.getTotalNaohFromOils(Soap_id, getActivity())));
        tvTotalOilWeight.setText(String.valueOf(calculator.getTotalOilsUsed(Soap_id, getActivity())));


    }

}
