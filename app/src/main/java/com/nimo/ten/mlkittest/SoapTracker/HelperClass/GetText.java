package com.nimo.ten.mlkittest.SoapTracker.HelperClass;

import android.widget.EditText;
import android.widget.TextView;

public class GetText {

    public String getText(TextView textView){

        String txtString = null;
        txtString = textView.getText().toString();

        return txtString;

    }



}
