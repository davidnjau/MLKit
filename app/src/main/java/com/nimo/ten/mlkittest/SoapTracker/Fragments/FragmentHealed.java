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
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapHealedRecyclerAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapTrackerPojo;

import java.util.ArrayList;
import java.util.List;


public class FragmentHealed extends Fragment {

    public FragmentHealed(){

    }

    RecyclerView recyclerView6;
    RecyclerView.LayoutManager layoutManager;

    DatabaseHelper databaseHelper;

    SoapHealedRecyclerAdapter SoapHealedRecyclerAdapter;
    List<SoapTrackerPojo> soapTrackerPojoArrayList;

    private LinearLayout myview;

    String txtCondition = "Healed";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_healing, container, false);

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView6 = view.findViewById(R.id.recyclerView6);
        myview = view.findViewById(R.id.myview);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        StartRecyclerView();
    }

    private void StartRecyclerView() {

        recyclerView6.setLayoutManager(layoutManager);
        recyclerView6.setHasFixedSize(true);

        databaseHelper = new DatabaseHelper(getActivity());

        soapTrackerPojoArrayList = databaseHelper.getSoapTrack(txtCondition);
        SoapHealedRecyclerAdapter = new SoapHealedRecyclerAdapter(getActivity(), (ArrayList<SoapTrackerPojo>) soapTrackerPojoArrayList);
        recyclerView6.setAdapter(SoapHealedRecyclerAdapter);

        if (SoapHealedRecyclerAdapter.getItemCount() == 0){

            myview.setVisibility(View.VISIBLE);
            recyclerView6.setVisibility(View.GONE);

        }else {

            myview.setVisibility(View.GONE);
            recyclerView6.setVisibility(View.VISIBLE);

        }

    }
}
