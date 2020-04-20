package com.nimo.ten.mlkittest.SoapTracker.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nimo.ten.mlkittest.SoapTracker.Fragments.FragmentHealed;
import com.nimo.ten.mlkittest.SoapTracker.Fragments.FragmentHealing;
import com.nimo.ten.mlkittest.SoapTracker.Fragments.FragmentIngredients;
import com.nimo.ten.mlkittest.SoapTracker.Fragments.FragmentLye;
import com.nimo.ten.mlkittest.SoapTracker.Fragments.FragmentNotes;

public class TabAdapterIngredientsNotes extends FragmentPagerAdapter {

    Context context;
    int totalTabs;


    public TabAdapterIngredientsNotes(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return new FragmentIngredients();

            case 1:
                return new FragmentLye();

            case 2:
                return new FragmentNotes();

            default:
                return null;



        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
