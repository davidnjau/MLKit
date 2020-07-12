package com.nimo.ten.mlkittest.SoapTracker.Fragments.drawer.bottomNavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nimo.ten.mlkittest.R;


public class FragmentWater extends Fragment {

    public FragmentWater(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_water, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

}
