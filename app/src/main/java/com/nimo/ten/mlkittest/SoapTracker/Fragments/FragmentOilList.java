package com.nimo.ten.mlkittest.SoapTracker.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Activities.SoapRecipe;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.OilsAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapHealedRecyclerAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapOilsAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapLyeLiquidsPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapTrackerPojo;

import java.util.ArrayList;
import java.util.List;


public class FragmentOilList extends Fragment {

    private List <String> mySelectedList;

    public FragmentOilList(){

    }

    RecyclerView recyclerView6;
    RecyclerView.LayoutManager layoutManager;

    DatabaseHelper databaseHelper;

    private SoapOilsAdapter oilsAdapter;
    private ArrayList<SoapLyeLiquidsPojo> soapLyeLiquidsPojoArrayList = new ArrayList<>();

    private LinearLayout myview;

    private Button btnConfirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_oil_list, container, false);

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView6 = view.findViewById(R.id.recyclerView6);
        myview = view.findViewById(R.id.myview);

        btnConfirm = view.findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mySelectedList = oilsAdapter.getList();

                for (int i = 0; i<mySelectedList.size(); i++){

                    String txtOilName = mySelectedList.get(i);
                    databaseHelper.AddMySoapOils(txtOilName);


                }

                startActivity(new Intent(getActivity(), SoapRecipe.class));

                Toast.makeText(getActivity(), "Oils added to your list.", Toast.LENGTH_SHORT).show();


            }
        });

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

        soapLyeLiquidsPojoArrayList = databaseHelper.getSoapOils();
        oilsAdapter = new SoapOilsAdapter(getActivity(), soapLyeLiquidsPojoArrayList);

        recyclerView6.setAdapter(oilsAdapter);

        if (oilsAdapter.getItemCount() == 0){

            myview.setVisibility(View.VISIBLE);
            recyclerView6.setVisibility(View.GONE);

        }else {

            myview.setVisibility(View.GONE);
            recyclerView6.setVisibility(View.VISIBLE);

        }

    }
}
