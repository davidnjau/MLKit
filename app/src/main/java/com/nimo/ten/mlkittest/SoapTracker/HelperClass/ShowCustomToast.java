package com.nimo.ten.mlkittest.SoapTracker.HelperClass;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.nimo.ten.mlkittest.R;

public class ShowCustomToast {

    TextView tvMessage;

    public ShowCustomToast(Activity a, String txtMessage) {

        LayoutInflater layoutInflater = a.getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.show_custom_toast, (ViewGroup) a.findViewById(R.id.custom_toast_layout_id));
        tvMessage = layout.findViewById(R.id.tvMessage);
        tvMessage.setText(txtMessage);
        Toast toast = new Toast(a);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);//setting the view of custom toast layout
        toast.show();

    }



}