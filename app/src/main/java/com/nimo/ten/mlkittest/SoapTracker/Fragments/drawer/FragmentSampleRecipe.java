package com.nimo.ten.mlkittest.SoapTracker.Fragments.drawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nimo.ten.mlkittest.R;

import java.util.Objects;

import static com.nimo.ten.mlkittest.SoapTracker.HelperClass.UtilKt.replaceFragmenty;


public class FragmentSampleRecipe extends Fragment {

    public FragmentSampleRecipe(){

    }

    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sample_recipe, container, false);

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {

            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainContent, new FragmentAdminRecipes());
            ft.addToBackStack(null);
            ft.commit();


        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

}
