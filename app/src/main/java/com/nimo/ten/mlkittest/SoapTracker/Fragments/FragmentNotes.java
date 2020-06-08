package com.nimo.ten.mlkittest.SoapTracker.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapNotesRecyclerAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsPojo;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class FragmentNotes extends Fragment {

    private SharedPreferences preferences;
    private String txtSoapId;
    private SoapNotesRecyclerAdapter soapDetailsRecyclerAdapter;
    private String PriorityLevel = "Normal";

    public FragmentNotes(){

    }

    RecyclerView recyclerView6;
    RecyclerView.LayoutManager layoutManager;

    DatabaseHelper databaseHelper;

    List<IngredientsPojo> soapTrackerPojoArrayList;

    EditText etNotes;
    private LinearLayout myview;

    private Spinner spinner;
    ArrayList<String> priority = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        layoutManager = new LinearLayoutManager(getActivity());
        preferences = getActivity().getSharedPreferences("Soap", MODE_PRIVATE);
        recyclerView6 = view.findViewById(R.id.recyclerView6);
        etNotes = view.findViewById(R.id.etNotes);

        spinner = view.findViewById(R.id.spinner);

        recyclerView6.setLayoutManager(layoutManager);
        recyclerView6.setHasFixedSize(true);
        myview = view.findViewById(R.id.myview);

        databaseHelper = new DatabaseHelper(getActivity());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                PriorityLevel = (String)  parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txtNotes = etNotes.getText().toString();

                if (!TextUtils.isEmpty(txtNotes)){

                    String Soap_id = preferences.getString("soap_id", null);

                    if (PriorityLevel.equals("Priority Level"))PriorityLevel = "Normal";

                    databaseHelper.AddNotes(Soap_id,txtNotes, PriorityLevel);

                    Toast.makeText(getActivity(), "Notes added successfully.", Toast.LENGTH_SHORT).show();
                    etNotes.setText("");

                    StartRecyclerView(txtSoapId);

                }else {

                    etNotes.setError("Notes field is empty..");

                }



            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        preferences = getActivity().getSharedPreferences("Soap", MODE_PRIVATE);
        txtSoapId = preferences.getString("soap_id", null);

        StartRecyclerView(txtSoapId);
    }

    private void StartRecyclerView(String txtSoapId) {

        recyclerView6.setLayoutManager(new LinearLayoutManager(getActivity()));

        soapTrackerPojoArrayList = databaseHelper.getSoapNotes(txtSoapId);

        soapDetailsRecyclerAdapter = new SoapNotesRecyclerAdapter(getActivity(), (ArrayList<IngredientsPojo>) soapTrackerPojoArrayList);
        recyclerView6.setAdapter(soapDetailsRecyclerAdapter);

        if (soapDetailsRecyclerAdapter.getItemCount() == 0){

            myview.setVisibility(View.VISIBLE);
            recyclerView6.setVisibility(View.GONE);

        }else {

            myview.setVisibility(View.GONE);
            recyclerView6.setVisibility(View.VISIBLE);

        }
    }


}
