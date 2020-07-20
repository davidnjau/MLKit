package com.nimo.ten.mlkittest.SoapTracker.Fragments.drawer;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nimo.ten.mlkittest.R;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class FragmentAboutUs extends Fragment {

    public FragmentAboutUs(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about_us, container, false);

        Element adsElement = new Element();
        View aboutPage = new AboutPage(getActivity())
                .isRTL(false)
                .setImage(R.mipmap.ic_pic_logo)
                .addItem(new Element().setTitle("Version 1.0"))
                .setDescription(getString(R.string.action_about))
                .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("info@nimonaturals.com")
                .addWebsite("http://nimonaturals.com/")
                .addFacebook("nimonaturals")
                .addTwitter("Nimonaturals")
//                .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                .addPlayStore("com.tech.centafrique")
                .addInstagram("nimonaturals")
                .addInstagram("nimonaturals")
//                .addGitHub("medyo")
                .addItem(getCopyRightsElement())
                .create();

        return aboutPage;
    }

    Element getCopyRightsElement() {

        Element copyRightsElement = new Element();

        final String copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));

        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.icon_person);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }


}
