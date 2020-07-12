package com.nimo.ten.mlkittest.SoapTracker.Fragments.drawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Fragments.drawer.bottomNavigation.FragmentAddOils;
import com.nimo.ten.mlkittest.SoapTracker.Fragments.drawer.bottomNavigation.FragmentFragrance;
import com.nimo.ten.mlkittest.SoapTracker.Fragments.drawer.bottomNavigation.FragmentWater;

import static com.nimo.ten.mlkittest.SoapTracker.HelperClass.UtilKt.replaceFragmenty;


public class FragmentStartRecipe extends Fragment {

    private Fragment fragment;

    public FragmentStartRecipe(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_start_recipe, container, false);

        BottomNavigationView navigation = view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        fragment = new FragmentAddOils();
        loadFragment(fragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_oils:
                    fragment = new FragmentAddOils();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_water:
                    fragment = new FragmentWater();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_fragrance:
                    fragment = new FragmentFragrance();
                    loadFragment(fragment);
                    return true;

            }

            return false;
        }

    };



    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
