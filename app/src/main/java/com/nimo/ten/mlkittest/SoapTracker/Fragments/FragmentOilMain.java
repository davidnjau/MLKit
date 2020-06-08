package com.nimo.ten.mlkittest.SoapTracker.Fragments;

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
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapLyeLiquidsPojo;

import java.util.ArrayList;


public class FragmentOilMain extends Fragment {

    public FragmentOilMain(){

    }

    private RecyclerView recyclerView;
    private SoapMyOilsAdapter oilsAdapter;
    private ArrayList<SoapLyeLiquidsPojo> soapLyeLiquidsPojoArrayList = new ArrayList<>();
    private DatabaseHelper databaseHelper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_oil, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        StartRecyclerView();
    }

    private void StartRecyclerView() {

        recyclerView.setHasFixedSize(true);

        databaseHelper = new DatabaseHelper(getActivity());

        soapLyeLiquidsPojoArrayList = databaseHelper.getMySoapOils();
        oilsAdapter = new SoapMyOilsAdapter(getActivity(), soapLyeLiquidsPojoArrayList);

        recyclerView.setAdapter(oilsAdapter);

    }

}
