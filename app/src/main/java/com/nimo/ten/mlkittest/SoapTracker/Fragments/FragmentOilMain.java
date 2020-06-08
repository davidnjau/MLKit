package com.nimo.ten.mlkittest.SoapTracker.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.MyOilsAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapMyOilsAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapOilsAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_oil, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        preferences = getActivity().getSharedPreferences("Soap", MODE_PRIVATE);


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

    }

}
