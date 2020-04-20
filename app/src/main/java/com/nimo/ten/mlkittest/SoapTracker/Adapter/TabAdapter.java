package com.nimo.ten.mlkittest.SoapTracker.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.nimo.ten.mlkittest.SoapTracker.Activities.SoapHeal_Healing;
import com.nimo.ten.mlkittest.SoapTracker.Fragments.FragmentHealed;
import com.nimo.ten.mlkittest.SoapTracker.Fragments.FragmentHealing;
import com.nimo.ten.mlkittest.SoapTracker.Fragments.FragmentLye;

public class TabAdapter extends FragmentPagerAdapter {

    Context context;
    int totalTabs;


    public TabAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return new FragmentHealing();

            case 1:
                return new FragmentHealed();


            default:
                return null;



        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
