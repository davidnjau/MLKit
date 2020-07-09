package com.nimo.ten.mlkittest.SoapTracker.Fragments.drawer;

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


public class FragmentNewRecipe extends Fragment {

    public FragmentNewRecipe(){

    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

}
